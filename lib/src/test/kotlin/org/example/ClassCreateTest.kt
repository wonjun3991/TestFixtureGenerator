import com.github.wonjun3991.testfixture.TestFixtureGenerator
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ClassCreateTest {
    @Test
    fun testOptionalWithRequiredString() {
        class ValVarTestClass(
            val optionalValue: String?,
            val requiredValue: String,
        )

        val generator = TestFixtureGenerator()
        val testClass = generator.create(ValVarTestClass::class)

        assertNotNull(testClass.optionalValue)
        assertNotNull(testClass.requiredValue)
    }

    @Test
    fun testFinalProperty() {
        class FinalPropertyClass(
            val finalValue: String,
            var mutableValue: String,
        )

        val generator = TestFixtureGenerator()
        val testClass = generator.create(FinalPropertyClass::class)

        assertNotNull(testClass.finalValue)
        assertNotNull(testClass.mutableValue)
    }

    @Test
    fun testBasicTypes() {
        class BasicClass(
            val int: Int,
            val long: Long,
            val double: Double,
            val float: Float,
            val boolean: Boolean,
            val string: String,
            val date: Date,
        )

        val generator = TestFixtureGenerator()
        val testClass = generator.create(BasicClass::class)

        assertNotNull(testClass.int)
        assertNotNull(testClass.long)
        assertNotNull(testClass.double)
        assertNotNull(testClass.float)
        assertNotNull(testClass.boolean)
        assertNotNull(testClass.string)
        assertNotNull(testClass.date)
    }

    @Test
    fun testListTypes() {
        class ListClass(
            val ints: List<Int>,
            val longs: List<Long>,
            val doubles: List<Double>,
            val floats: List<Float>,
            val booleans: List<Boolean>,
            val strings: List<String>,
        )

        val generator = TestFixtureGenerator()
        val testClass = generator.create(ListClass::class)

        assertNotNull(testClass.ints)
        assertTrue { testClass.ints.isNotEmpty() }

        assertNotNull(testClass.longs)
        assertTrue { testClass.longs.isNotEmpty() }

        assertNotNull(testClass.doubles)
        assertTrue { testClass.doubles.isNotEmpty() }

        assertNotNull(testClass.floats)
        assertTrue { testClass.floats.isNotEmpty() }

        assertNotNull(testClass.booleans)
        assertTrue { testClass.booleans.isNotEmpty() }

        assertNotNull(testClass.strings)
        assertTrue { testClass.strings.isNotEmpty() }
    }

    @Test
    fun testSetTypes() {
        class SetClass(
            val ints: Set<Int>,
            val longs: Set<Long>,
            val doubles: Set<Double>,
            val floats: Set<Float>,
            val booleans: Set<Boolean>,
            val strings: Set<String>,
        )

        val generator = TestFixtureGenerator()
        val testClass = generator.create(SetClass::class)

        assertNotNull(testClass.ints)
        assertTrue { testClass.ints.isNotEmpty() }

        assertNotNull(testClass.longs)
        assertTrue { testClass.longs.isNotEmpty() }

        assertNotNull(testClass.doubles)
        assertTrue { testClass.doubles.isNotEmpty() }

        assertNotNull(testClass.floats)
        assertTrue { testClass.floats.isNotEmpty() }

        assertNotNull(testClass.booleans)
        assertTrue { testClass.booleans.isNotEmpty() }

        assertNotNull(testClass.strings)
        assertTrue { testClass.strings.isNotEmpty() }
    }

    @Test
    fun testMapTypes() {
        class MapClass(
            val ints: Map<Int, Int>,
            val longs: Map<Long, Long>,
            val doubles: Map<Double, Double>,
            val floats: Map<Float, Float>,
            val booleans: Map<Boolean, Boolean>,
            val strings: Map<String, String>,
            val intToString: Map<Int, String>,
        )

        val generator = TestFixtureGenerator()
        val testClass = generator.create(MapClass::class)

        assertNotNull(testClass.ints)
        assertTrue { testClass.ints.isNotEmpty() }

        assertNotNull(testClass.longs)
        assertTrue { testClass.longs.isNotEmpty() }

        assertNotNull(testClass.doubles)
        assertTrue { testClass.doubles.isNotEmpty() }

        assertNotNull(testClass.floats)
        assertTrue { testClass.floats.isNotEmpty() }

        assertNotNull(testClass.booleans)
        assertTrue { testClass.booleans.isNotEmpty() }

        assertNotNull(testClass.strings)
        assertTrue { testClass.strings.isNotEmpty() }

        assertNotNull(testClass.intToString)
        assertTrue { testClass.intToString.keys.isNotEmpty() }
        assertTrue { testClass.intToString.keys.all { it::class == Int::class } }
        assertTrue { testClass.intToString.values.isNotEmpty() }
        assertTrue { testClass.intToString.values.all { it::class == String::class } }
    }
}
