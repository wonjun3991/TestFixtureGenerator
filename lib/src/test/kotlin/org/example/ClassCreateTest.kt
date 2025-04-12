import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertNotNull

class ClassCreateTest {
    @Test
    fun testOptionalWithRequiredString(){
        class ValVarTestClass (
            val optionalValue: String?,
            val requiredValue: String,
        )

        val generator = TestFixtureGenerator()
        val testClass = generator.create(ValVarTestClass::class)

        assertNotNull(testClass.optionalValue)
        assertNotNull(testClass.requiredValue)
    }

    @Test
    fun testFinalProperty(){
        class FinalPropertyClass (
            val finalValue: String,
            var mutableValue: String,
        )

        val generator = TestFixtureGenerator()
        val testClass = generator.create(FinalPropertyClass::class)

        assertNotNull(testClass.finalValue)
        assertNotNull(testClass.mutableValue)
    }

    @Test
    fun testBasicTypes(){
        class BasicClass (
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
}
