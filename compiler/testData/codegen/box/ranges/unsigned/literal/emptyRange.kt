// KJS_WITH_FULL_RUNTIME
// Auto-generated by org.jetbrains.kotlin.generators.tests.GenerateRangesCodegenTestData. DO NOT EDIT!
// WITH_RUNTIME



fun box(): String {
    val list1 = ArrayList<UInt>()
    for (i in 10u..5u) {
        list1.add(i)
        if (list1.size > 23) break
    }
    if (list1 != listOf<UInt>()) {
        return "Wrong elements for 10u..5u: $list1"
    }

    val list2 = ArrayList<UInt>()
    for (i in 10u.toUByte()..5u.toUByte()) {
        list2.add(i)
        if (list2.size > 23) break
    }
    if (list2 != listOf<UInt>()) {
        return "Wrong elements for 10u.toUByte()..5u.toUByte(): $list2"
    }

    val list3 = ArrayList<UInt>()
    for (i in 10u.toUShort()..5u.toUShort()) {
        list3.add(i)
        if (list3.size > 23) break
    }
    if (list3 != listOf<UInt>()) {
        return "Wrong elements for 10u.toUShort()..5u.toUShort(): $list3"
    }

    val list4 = ArrayList<ULong>()
    for (i in 10uL..5uL) {
        list4.add(i)
        if (list4.size > 23) break
    }
    if (list4 != listOf<ULong>()) {
        return "Wrong elements for 10uL..5uL: $list4"
    }

    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: STDLIB_ARRAY_LIST
