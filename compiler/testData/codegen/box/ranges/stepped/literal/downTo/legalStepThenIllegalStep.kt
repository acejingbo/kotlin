// Auto-generated by GenerateSteppedRangesCodegenTestData. Do not edit!
// KJS_WITH_FULL_RUNTIME
// WITH_RUNTIME
import kotlin.test.*

fun box(): String {
    assertFailsWith<IllegalArgumentException> {
        for (i in 7 downTo 1 step 2 step 0) {
        }
    }

    assertFailsWith<IllegalArgumentException> {
        for (i in 7L downTo 1L step 2L step 0L) {
        }
    }

    assertFailsWith<IllegalArgumentException> {
        for (i in 'g' downTo 'a' step 2 step 0) {
        }
    }

    return "OK"
}
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: KOTLIN_TEST_LIB
