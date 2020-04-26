// KJS_WITH_FULL_RUNTIME
// Auto-generated by org.jetbrains.kotlin.generators.tests.GenerateRangesCodegenTestData. DO NOT EDIT!
// WITH_RUNTIME



fun box(): String {
    val list1 = ArrayList<Int>()
    for (i in (3 downTo 5).reversed()) {
        list1.add(i)
        if (list1.size > 23) break
    }
    if (list1 != listOf<Int>()) {
        return "Wrong elements for (3 downTo 5).reversed(): $list1"
    }

    val list2 = ArrayList<Int>()
    for (i in (3.toByte() downTo 5.toByte()).reversed()) {
        list2.add(i)
        if (list2.size > 23) break
    }
    if (list2 != listOf<Int>()) {
        return "Wrong elements for (3.toByte() downTo 5.toByte()).reversed(): $list2"
    }

    val list3 = ArrayList<Int>()
    for (i in (3.toShort() downTo 5.toShort()).reversed()) {
        list3.add(i)
        if (list3.size > 23) break
    }
    if (list3 != listOf<Int>()) {
        return "Wrong elements for (3.toShort() downTo 5.toShort()).reversed(): $list3"
    }

    val list4 = ArrayList<Long>()
    for (i in (3L downTo 5L).reversed()) {
        list4.add(i)
        if (list4.size > 23) break
    }
    if (list4 != listOf<Long>()) {
        return "Wrong elements for (3L downTo 5L).reversed(): $list4"
    }

    val list5 = ArrayList<Char>()
    for (i in ('a' downTo 'c').reversed()) {
        list5.add(i)
        if (list5.size > 23) break
    }
    if (list5 != listOf<Char>()) {
        return "Wrong elements for ('a' downTo 'c').reversed(): $list5"
    }

    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: STDLIB_ARRAY_LIST
