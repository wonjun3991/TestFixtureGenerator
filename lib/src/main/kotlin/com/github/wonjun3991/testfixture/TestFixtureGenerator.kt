package com.github.wonjun3991.testfixture

import java.util.*
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

object TestFixtureGenerator {
    private val fixedValues = mutableMapOf<KClass<*>, Any?>()

    fun <T : Any> registerValue(type: KClass<T>, value: T) {
        fixedValues[type] = value
    }

    fun <T : Any> create(kClass: KClass<T>): T {
        fixedValues[kClass]?.let { @Suppress("UNCHECKED_CAST") return it as T }

        val constructor = kClass.primaryConstructor
            ?: kClass.constructors.firstOrNull()
            ?: throw IllegalArgumentException("No constructor for ${kClass.simpleName}")
        constructor.isAccessible = true

        val args = constructor.parameters
            .filterNot { it.isOptional }
            .associateWith { generateRandomValue(it.type) }

        val instance = constructor.callBy(args)

        kClass.memberProperties.toList()
            .forEach { property ->
                property.javaField
                    ?.apply { isAccessible = true }
                    ?.set(instance, generateRandomValue(property.returnType))
            }

        return instance
    }

    private fun generateRandomValue(type: KType): Any? {
        val klass = type.classifier as? KClass<*>
        if (klass != null && fixedValues.containsKey(klass)) {
            return fixedValues[klass]
        }

        return when (klass) {
            Int::class -> Random.nextInt()
            Long::class -> Random.nextLong()
            Double::class -> Random.nextDouble()
            Float::class -> Random.nextFloat()
            Boolean::class -> Random.nextBoolean()
            String::class -> UUID.randomUUID().toString()
            Date::class -> Date(System.currentTimeMillis() - Random.nextLong(100_000))

            List::class -> {
                val elemType = type.arguments.firstOrNull()?.type
                    ?: return emptyList<KType>()
                List(Random.nextInt(1, 5)) { generateRandomValue(elemType) }
            }

            Set::class -> {
                val elemType = type.arguments.firstOrNull()?.type
                    ?: return emptySet<Any?>()
                (1..Random.nextInt(1, 5))
                    .map { generateRandomValue(elemType) }
                    .toSet()
            }

            Map::class -> {
                val (k, v) = type.arguments
                (1..Random.nextInt(1, 5)).associate {
                    generateRandomValue(k.type!!) to generateRandomValue(v.type!!)
                }
            }

            Array::class -> {

            }

            is KClass<*> -> {
                @Suppress("UNCHECKED_CAST")
                create(klass as KClass<Any>)
            }

            else -> null
        }
    }
}
