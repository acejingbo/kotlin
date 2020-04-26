// KJS_WITH_FULL_RUNTIME
// WITH_RUNTIME

val xs = listOf("a", "b", "c", "d").asSequence()

fun box(): String {
    val s = StringBuilder()

    for ((i, _) in xs.withIndex()) {
        s.append("$i;")
    }

    val ss = s.toString()
    return if (ss == "0;1;2;3;") "OK" else "fail: '$ss'"
}


// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: STDLIB_GENERATED
