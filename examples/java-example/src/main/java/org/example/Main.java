package org.example;

import com.github.wonjun3991.testfixture.TestFixtureGenerator;




public class Main {
    public static void main(String[] args) {
        System.out.println("test");
        Person person = TestFixtureGenerator.create(Person.class);
        System.out.println(person.name());
        System.out.println(person.age());
    }
}
