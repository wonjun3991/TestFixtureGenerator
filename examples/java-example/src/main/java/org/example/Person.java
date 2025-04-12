package org.example;

class Person{
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private final String name;
    private final int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
