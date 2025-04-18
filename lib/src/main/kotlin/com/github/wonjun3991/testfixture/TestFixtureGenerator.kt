package com.github.wonjun3991.testfixture

import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*
import kotlin.random.Random
import kotlin.reflect.KClass

object TestFixtureGenerator {
    private val fixedValues = mutableMapOf<Class<*>, Any?>()

    @JvmStatic
    fun <T : Any> registerValue(type: Class<T>, value: T) {
        fixedValues[type] = value
    }

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> create(clazz: Class<T>): T {
        fixedValues[clazz]?.let { return it as T }

        val ctor = clazz.declaredConstructors
            .firstOrNull { it.parameterCount > 0 }
            ?: clazz.declaredConstructors.firstOrNull()
            ?: throw IllegalArgumentException("No constructor for ${clazz.simpleName}")
        ctor.isAccessible = true

        val args = ctor.genericParameterTypes.map { generateRandomValue(it) }.toTypedArray()
        val instance = ctor.newInstance(*args) as T

        clazz.declaredFields.forEach { field ->
            field.isAccessible = true
            field.set(instance, generateRandomValue(field.genericType))
        }

        return instance
    }

    inline fun <reified T : Any> create(kClass: KClass<T>): T =
        create(kClass.java)

    fun <T : Any> registerValue(kClass: KClass<T>, value: T) =
        registerValue(kClass.java, value)

    private fun generateRandomValue(type: Type): Any? {
        if (type is Class<*> && fixedValues.containsKey(type)) {
            return fixedValues[type]
        }

        when (type) {
            Int::class.javaPrimitiveType, Integer::class.java -> return Random.nextInt()
            Long::class.javaPrimitiveType, java.lang.Long::class.java -> return Random.nextLong()
            Double::class.javaPrimitiveType, java.lang.Double::class.java -> return Random.nextDouble()
            Float::class.javaPrimitiveType, java.lang.Float::class.java -> return Random.nextFloat()
            Boolean::class.javaPrimitiveType, java.lang.Boolean::class.java -> return Random.nextBoolean()
            String::class.java -> return UUID.randomUUID().toString()
            Date::class.java -> return Date(System.currentTimeMillis() - Random.nextLong(100_000))
        }

        val (compType, isArray) = when {
            type is GenericArrayType -> type.genericComponentType to true
            type is Class<*> && type.isArray -> (type.componentType as Type) to true
            else -> null to false
        }
        if (isArray) {
            val len = Random.nextInt(1, 5)
            return Array(len) { generateRandomValue(compType!!) }
        }

        if (type is ParameterizedType) {
            val raw = type.rawType as Class<*>
            val args = type.actualTypeArguments
            return when {
                List::class.java.isAssignableFrom(raw) -> {
                    val et = args.getOrNull(0) ?: return emptyList<Any?>()
                    List(Random.nextInt(1, 5)) { generateRandomValue(et) }
                }

                Set::class.java.isAssignableFrom(raw) -> {
                    val et = args.getOrNull(0) ?: return emptySet<Any?>()
                    (1..Random.nextInt(1, 5)).map { generateRandomValue(et) }.toSet()
                }

                Map::class.java.isAssignableFrom(raw) -> {
                    val kt = args.getOrNull(0)!!
                    val vt = args.getOrNull(1)!!
                    (1..Random.nextInt(1, 5)).associate {
                        generateRandomValue(kt) to generateRandomValue(vt)
                    }
                }

                else -> null
            }
        }

        if (type is Class<*>) {
            @Suppress("UNCHECKED_CAST")
            return create(type as Class<Any>)
        }
        return null
    }
}
