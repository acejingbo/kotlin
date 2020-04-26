// Auto-generated by GenerateSteppedRangesCodegenTestData. Do not edit!
// KJS_WITH_FULL_RUNTIME
// WITH_RUNTIME
// KT-34166: Translation of loop over literal completely removes the validation of step
// DONT_TARGET_EXACT_BACKEND: JS
import kotlin.test.*

fun box(): String {
    assertFailsWith<IllegalArgumentException> {
        for (i in 1 until 8 step -1) {
        }
    }

    assertFailsWith<IllegalArgumentException> {
        for (i in 1L until 8L step -1L) {
        }
    }

    assertFailsWith<IllegalArgumentException> {
        for (i in 'a' until 'h' step -1) {
        }
    }

    return "OK"
}
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: KOTLIN_TEST_LIB
