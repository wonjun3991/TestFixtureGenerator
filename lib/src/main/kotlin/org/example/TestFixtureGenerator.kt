import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaField
import java.util.Date
import java.util.UUID
import kotlin.random.Random
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
            Int::class    -> Random.nextInt()
            Long::class   -> Random.nextLong()
            Double::class -> Random.nextDouble()
            Float::class  -> Random.nextFloat()
            Boolean::class-> Random.nextBoolean()
            String::class -> UUID.randomUUID().toString()
            Date::class   -> Date()
            Date::class    -> Date()
            is KClass<*>   -> {
                @Suppress("UNCHECKED_CAST")
                create(type.classifier as KClass<Any>)
            }
            else          -> null
        }
    }
}
