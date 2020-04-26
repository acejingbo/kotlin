// KJS_WITH_FULL_RUNTIME
// Auto-generated by org.jetbrains.kotlin.generators.tests.GenerateRangesCodegenTestData. DO NOT EDIT!
// WITH_RUNTIME


const val MinI = Int.MIN_VALUE
const val MinB = Byte.MIN_VALUE
const val MinS = Short.MIN_VALUE
const val MinL = Long.MIN_VALUE
const val MinC = Char.MIN_VALUE

fun box(): String {
    val list1 = ArrayList<Int>()
    for (i in MinI..MinI step 1) {
        list1.add(i)
        if (list1.size > 23) break
    }
    if (list1 != listOf<Int>(MinI)) {
        return "Wrong elements for MinI..MinI step 1: $list1"
    }

    val list2 = ArrayList<Int>()
    for (i in MinB..MinB step 1) {
        list2.add(i)
        if (list2.size > 23) break
    }
    if (list2 != listOf<Int>(MinB.toInt())) {
        return "Wrong elements for MinB..MinB step 1: $list2"
    }

    val list3 = ArrayList<Int>()
    for (i in MinS..MinS step 1) {
        list3.add(i)
        if (list3.size > 23) break
    }
    if (list3 != listOf<Int>(MinS.toInt())) {
        return "Wrong elements for MinS..MinS step 1: $list3"
    }

    val list4 = ArrayList<Long>()
    for (i in MinL..MinL step 1) {
        list4.add(i)
        if (list4.size > 23) break
    }
    if (list4 != listOf<Long>(MinL)) {
        return "Wrong elements for MinL..MinL step 1: $list4"
    }

    val list5 = ArrayList<Char>()
    for (i in MinC..MinC step 1) {
        list5.add(i)
        if (list5.size > 23) break
    }
    if (list5 != listOf<Char>(MinC)) {
        return "Wrong elements for MinC..MinC step 1: $list5"
    }

    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: STDLIB_ARRAY_LIST
