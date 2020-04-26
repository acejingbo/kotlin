// KJS_WITH_FULL_RUNTIME
// Auto-generated by GenerateInRangeExpressionTestData. Do not edit!
// WITH_RUNTIME



val range0 = 3L downTo 1L
val range1 = 1L downTo 3L

val element0 = 1L

fun box(): String {
    testR0xE0()
    testR1xE0()
    return "OK"
}

fun testR0xE0() {
    // with possible local optimizations
    if (1L in 3L downTo 1L != range0.contains(1L)) throw AssertionError()
    if (1L !in 3L downTo 1L != !range0.contains(1L)) throw AssertionError()
    if (!(1L in 3L downTo 1L) != !range0.contains(1L)) throw AssertionError()
    if (!(1L !in 3L downTo 1L) != range0.contains(1L)) throw AssertionError()
    // no local optimizations
    if (element0 in 3L downTo 1L != range0.contains(element0)) throw AssertionError()
    if (element0 !in 3L downTo 1L != !range0.contains(element0)) throw AssertionError()
    if (!(element0 in 3L downTo 1L) != !range0.contains(element0)) throw AssertionError()
    if (!(element0 !in 3L downTo 1L) != range0.contains(element0)) throw AssertionError()
}

fun testR1xE0() {
    // with possible local optimizations
    if (1L in 1L downTo 3L != range1.contains(1L)) throw AssertionError()
    if (1L !in 1L downTo 3L != !range1.contains(1L)) throw AssertionError()
    if (!(1L in 1L downTo 3L) != !range1.contains(1L)) throw AssertionError()
    if (!(1L !in 1L downTo 3L) != range1.contains(1L)) throw AssertionError()
    // no local optimizations
    if (element0 in 1L downTo 3L != range1.contains(element0)) throw AssertionError()
    if (element0 !in 1L downTo 3L != !range1.contains(element0)) throw AssertionError()
    if (!(element0 in 1L downTo 3L) != !range1.contains(element0)) throw AssertionError()
    if (!(element0 !in 1L downTo 3L) != range1.contains(element0)) throw AssertionError()
}



// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: STDLIB_COLLECTIONS
