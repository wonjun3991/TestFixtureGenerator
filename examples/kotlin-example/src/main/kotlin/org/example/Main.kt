package org.example

import com.github.wonjun3991.testfixture.TestFixtureGenerator


data class User(var id: Int = 0, var name: String = "")

fun main() {
    val user = TestFixtureGenerator.create(User::class)
    println(user)
}
