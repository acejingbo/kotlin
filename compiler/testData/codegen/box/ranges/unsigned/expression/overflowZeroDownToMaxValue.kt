// IGNORE_BACKEND_FIR: JVM_IR
// KJS_WITH_FULL_RUNTIME
// Auto-generated by org.jetbrains.kotlin.generators.tests.GenerateRangesCodegenTestData. DO NOT EDIT!
// WITH_RUNTIME


const val MaxUI = UInt.MAX_VALUE
const val MaxUL = ULong.MAX_VALUE

fun box(): String {
    val list1 = ArrayList<UInt>()
    val range1 = 0u downTo MaxUI step 3
    for (i in range1) {
        list1.add(i)
        if (list1.size > 23) break
    }
    if (list1 != listOf<UInt>()) {
        return "Wrong elements for 0u downTo MaxUI step 3: $list1"
    }

    val list2 = ArrayList<ULong>()
    val range2 = 0uL downTo MaxUL step 3
    for (i in range2) {
        list2.add(i)
        if (list2.size > 23) break
    }
    if (list2 != listOf<ULong>()) {
        return "Wrong elements for 0uL downTo MaxUL step 3: $list2"
    }

    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: STDLIB_ARRAY_LIST
