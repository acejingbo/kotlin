// !LANGUAGE: +NewInference +FunctionReferenceWithDefaultValueAsOtherType
// IGNORE_BACKEND_FIR: JVM_IR

fun foo(s: String = "kotlin", vararg t: String): Boolean {
    if (s != "kotlin") throw AssertionError(s)
    if (t.size != 0) throw AssertionError(t.size.toString())
    return true
}

fun bar(f: () -> Unit) {
    f()
}

fun box(): String {
    bar(::foo)
    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: IGNORED_IN_JS
