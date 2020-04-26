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
    for (i in (MinI + 5) downTo MinI step 3) {
        list1.add(i)
        if (list1.size > 23) break
    }
    if (list1 != listOf<Int>(MinI + 5, MinI + 2)) {
        return "Wrong elements for (MinI + 5) downTo MinI step 3: $list1"
    }

    val list2 = ArrayList<Int>()
    for (i in (MinB + 5).toByte() downTo MinB step 3) {
        list2.add(i)
        if (list2.size > 23) break
    }
    if (list2 != listOf<Int>((MinB + 5).toInt(), (MinB + 2).toInt())) {
        return "Wrong elements for (MinB + 5).toByte() downTo MinB step 3: $list2"
    }

    val list3 = ArrayList<Int>()
    for (i in (MinS + 5).toShort() downTo MinS step 3) {
        list3.add(i)
        if (list3.size > 23) break
    }
    if (list3 != listOf<Int>((MinS + 5).toInt(), (MinS + 2).toInt())) {
        return "Wrong elements for (MinS + 5).toShort() downTo MinS step 3: $list3"
    }

    val list4 = ArrayList<Long>()
    for (i in (MinL + 5).toLong() downTo MinL step 3) {
        list4.add(i)
        if (list4.size > 23) break
    }
    if (list4 != listOf<Long>((MinL + 5).toLong(), (MinL + 2).toLong())) {
        return "Wrong elements for (MinL + 5).toLong() downTo MinL step 3: $list4"
    }

    val list5 = ArrayList<Char>()
    for (i in (MinC + 5) downTo MinC step 3) {
        list5.add(i)
        if (list5.size > 23) break
    }
    if (list5 != listOf<Char>((MinC + 5), (MinC + 2))) {
        return "Wrong elements for (MinC + 5) downTo MinC step 3: $list5"
    }

    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: STDLIB_ARRAY_LIST
