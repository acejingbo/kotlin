// KJS_WITH_FULL_RUNTIME
// Auto-generated by org.jetbrains.kotlin.generators.tests.GenerateRangesCodegenTestData. DO NOT EDIT!
// WITH_RUNTIME



fun box(): String {
    val list1 = ArrayList<Int>()
    val range1 = 5 downTo 10
    for (i in range1) {
        list1.add(i)
        if (list1.size > 23) break
    }
    if (list1 != listOf<Int>()) {
        return "Wrong elements for 5 downTo 10: $list1"
    }

    val list2 = ArrayList<Int>()
    val range2 = 5.toByte() downTo 10.toByte()
    for (i in range2) {
        list2.add(i)
        if (list2.size > 23) break
    }
    if (list2 != listOf<Int>()) {
        return "Wrong elements for 5.toByte() downTo 10.toByte(): $list2"
    }

    val list3 = ArrayList<Int>()
    val range3 = 5.toShort() downTo 10.toShort()
    for (i in range3) {
        list3.add(i)
        if (list3.size > 23) break
    }
    if (list3 != listOf<Int>()) {
        return "Wrong elements for 5.toShort() downTo 10.toShort(): $list3"
    }

    val list4 = ArrayList<Long>()
    val range4 = 5L downTo 10L
    for (i in range4) {
        list4.add(i)
        if (list4.size > 23) break
    }
    if (list4 != listOf<Long>()) {
        return "Wrong elements for 5L downTo 10L: $list4"
    }

    val list5 = ArrayList<Char>()
    val range5 = 'a' downTo 'z'
    for (i in range5) {
        list5.add(i)
        if (list5.size > 23) break
    }
    if (list5 != listOf<Char>()) {
        return "Wrong elements for 'a' downTo 'z': $list5"
    }

    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: STDLIB_ARRAY_LIST
