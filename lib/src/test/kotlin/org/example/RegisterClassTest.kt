package org.example

import com.github.wonjun3991.testfixture.TestFixtureGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class RegisterClassTest {
    @Test
    fun registerClassTest() {
        data class User(val id: Int = 0, val name: String = "", val active: Boolean = false)

        val gen = TestFixtureGenerator()

        val fixedUser = User(id = 999, name = "고정유저", active = true)
        gen.registerValue(User::class, fixedUser)

        val user = gen.create(User::class)

        assertEquals(fixedUser.id, user.id)
        assertEquals(fixedUser.name, user.name)
        assertEquals(fixedUser.active, user.active)
    }

    @Test
    fun registerNestedClassTest() {
        data class Address(val id: Int = 0, val street: String = "", val city: String = "")
        data class User(val id: Int = 0, val name: String = "", val active: Boolean = false, val address: Address)

        val gen = TestFixtureGenerator()
        val fixedAddress = Address(street = "강남대로", city = "서울")
        gen.registerValue(Address::class, fixedAddress)

        val user = gen.create(User::class)

        assertEquals(fixedAddress.id, user.address.id)
        assertEquals(fixedAddress.street, user.address.street)
        assertEquals(fixedAddress.city, user.address.city)
    }
}
