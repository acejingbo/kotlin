// KJS_WITH_FULL_RUNTIME
// WITH_RUNTIME

import kotlin.test.assertEquals

fun box(): String {
    val indexList = mutableListOf<Int>()
    val valueList = mutableListOf<Int>()
    for (iv in (4..7).withIndex()) {
        val (i, v) = iv
        indexList += i
        valueList += v
    }
    assertEquals(listOf(0, 1, 2, 3), indexList)
    assertEquals(listOf(4, 5, 6, 7), valueList)

    return "OK"
}
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: STDLIB_ARRAY_LIST
