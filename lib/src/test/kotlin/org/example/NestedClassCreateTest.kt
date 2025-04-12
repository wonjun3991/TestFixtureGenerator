package org.example

import com.github.wonjun3991.testfixture.TestFixtureGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import kotlin.test.Test

class NestedClassCreateTest {
    @Test
    fun testNestedTypes(){
        data class Address(val city: String = "Seoul", val zip: Int = 12345)
        data class User(val name: String = "kim", val age: Int = 20, val address: Address)

        val gen = TestFixtureGenerator()
        val user = gen.create(User::class)

        // Type Check
        assertEquals(user::class, User::class)
        assertEquals(user.address::class, Address::class)

        // Value Check
        assertNotNull(user.name)
        assertNotNull(user.age)
        assertNotNull(user.address)

        assertNotNull(user.address.city)
        assertNotNull(user.address.zip)
    }
}
