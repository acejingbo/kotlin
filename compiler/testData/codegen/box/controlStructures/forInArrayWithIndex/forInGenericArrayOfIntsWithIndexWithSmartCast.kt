// KJS_WITH_FULL_RUNTIME
// WITH_RUNTIME

val arr = arrayOf(10, 20, 30, 40)

fun foo(xs: Any): String {
    if (xs !is Array<*>) return "not an Array<*>"

    val s = StringBuilder()
    for ((index, x) in xs.withIndex()) {
        s.append("$index:$x;")
    }
    return s.toString()
}

fun box(): String {
    val ss = foo(arr)
    return if (ss == "0:10;1:20;2:30;3:40;") "OK" else "fail: '$ss'"
}


// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: STDLIB_STRING_BUILDER
