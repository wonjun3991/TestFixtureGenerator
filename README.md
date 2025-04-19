# 🧪 TestFixtureGenerator

`TestFixtureGenerator`는 테스트 코드 작성 시 객체 생성의 번거로움을 줄이기 위해 만들어진 Kotlin 기반 유틸리티입니다.  
리플렉션을 이용하여 어떤 클래스든지 자동으로 임의의 값이 채워진 인스턴스를 생성할 수 있습니다.

---

## 🚀 프로젝트 개요

- 테스트에 필요한 **복잡한 도메인 객체**를 자동으로 생성해줍니다.
- **기본 타입 및 컬렉션, 배열, 제네릭 타입**까지 자동으로 랜덤한 값으로 채워줍니다.
- 필요 시 특정 타입에 대해 **고정 값 등록**도 지원합니다.
- Kotlin & Java에서 모두 사용 가능합니다.

---

## 📦 설치 방법

Gradle 프로젝트에 아래와 같이 jitpack을 추가하세요.

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.wonjun3991:TestFixtureGenerator:<version>")
}
```

## ✨ 사용 방법

```kotlin
data class Person(val name: String, val age: Int)

val person = TestFixtureGenerator.create(Person::class)
println(person) // 예: Person(name=3f2a4c59-1c93-4c8c..., age=29384)
```

```java
Person person = TestFixtureGenerator.create(Person.class);
System.out.println(person.name()); // f2da42b7-7192-47e7-a497-66cfe02f0cf6
System.out.println(person.age()); // 1499725037
```

특정 타입에 고정 값 설정하기

```kotlin
TestFixtureGenerator.registerValue(String::class, "홍길동")

val fixedPerson = TestFixtureGenerator.create(Person::class)
println(fixedPerson.name) // 항상 "홍길동" 출력
```


## 📄 License

Copyright 2025 wonjun3991

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
