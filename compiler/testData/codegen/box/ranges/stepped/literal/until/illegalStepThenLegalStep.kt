// Auto-generated by GenerateSteppedRangesCodegenTestData. Do not edit!
// KJS_WITH_FULL_RUNTIME
// WITH_RUNTIME
import kotlin.test.*

fun box(): String {
    assertFailsWith<IllegalArgumentException> {
        for (i in 1 until 8 step 0 step 2) {
        }
    }

    assertFailsWith<IllegalArgumentException> {
        for (i in 1L until 8L step 0L step 2L) {
        }
    }

    assertFailsWith<IllegalArgumentException> {
        for (i in 'a' until 'h' step 0 step 2) {
        }
    }

    return "OK"
}
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: KOTLIN_TEST_LIB
