fun <T> foo(t: T) {
    t!!
}

fun box(): String {
    try {
        foo<Any?>(null)
    } catch (e: Exception) {
        return "OK"
    }
    return "Fail"
}

// DONT_TARGET_EXACT_BACKEND: WASM
//DONT_TARGET_WASM_REASON: IR_TRY
