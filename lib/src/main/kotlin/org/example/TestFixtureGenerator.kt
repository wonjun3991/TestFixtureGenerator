import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaField
import java.util.Date
import java.util.UUID
import kotlin.random.Random
import kotlin.reflect.full.createType
import kotlin.reflect.jvm.isAccessible

class TestFixtureGenerator {
    fun <T : Any> create(kClass: KClass<T>): T {
        val constructor = kClass.primaryConstructor
            ?: throw IllegalArgumentException("No primary constructor for ${kClass.simpleName}")
        constructor.isAccessible = true

        val args = constructor.parameters
            .filter { !it.isOptional }
            .associateWith { generateRandomValue(it.type) }

        val instance = constructor.callBy(args)

        kClass.memberProperties
            .filterIsInstance<KMutableProperty1<T, Any?>>()
            .forEach { property ->
                property.javaField
                    ?.apply { isAccessible = true }
                    ?.let { field ->
                        val randomValue = generateRandomValue(property.returnType)
                        field.set(instance, randomValue)
                    }
            }

        return instance
    }

    private fun generateRandomValue(type: KType): Any? {
        return when (type.classifier) {
            Int::class -> Random.nextInt()
            Long::class -> Random.nextLong()
            Double::class -> Random.nextDouble()
            Float::class -> Random.nextFloat()
            Boolean::class -> Random.nextBoolean()
            String::class -> UUID.randomUUID().toString()
            Date::class -> Date()
            List::class -> List(Random.nextInt(1, 5)) { generateRandomValue(type.arguments.firstOrNull()?.type!!) }
            MutableList::class -> List(Random.nextInt(1, 5)) { generateRandomValue(type.arguments.firstOrNull()?.type!!) }
            Set::class -> (1..Random.nextInt(1, 5)).map { generateRandomValue(type.arguments.firstOrNull()?.type!!) }.toSet()
            MutableSet::class -> (1..Random.nextInt(1, 5)).map { generateRandomValue(type.arguments.firstOrNull()?.type!!) }.toSet()
            Map::class -> (1..Random.nextInt(1, 5)).associate {
                generateRandomValue(type.arguments.first().type!!) to generateRandomValue(type.arguments.last().type!!)
            }

            MutableMap::class -> (1..Random.nextInt(1, 5)).associate {
                generateRandomValue(type.arguments.first().type!!) to generateRandomValue(type.arguments.last().type!!)
            }

            is KClass<*> -> {
                @Suppress("UNCHECKED_CAST")
                create(type.classifier as KClass<Any>)
            }

            else -> null
        }
    }
}
