package org.example;

import com.github.wonjun3991.testfixture.TestFixtureGenerator;
import kotlin.jvm.JvmClassMappingKt;


public class Main {
    public static void main(String[] args) {
        TestFixtureGenerator gen = TestFixtureGenerator.INSTANCE;
        Person person = gen.create(JvmClassMappingKt.getKotlinClass(Person.class));
        System.out.println(person.name());
        System.out.println(person.age());
    }
}
