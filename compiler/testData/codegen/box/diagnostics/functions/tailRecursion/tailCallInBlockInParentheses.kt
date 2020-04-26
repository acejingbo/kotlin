// DONT_RUN_GENERATED_CODE: JS

tailrec fun foo(x: Int) {
    return if (x > 0) {
        (foo(x - 1))
    }
    else Unit
}

fun box(): String {
    foo(1000000)
    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
//DONT_TARGET_WASM_REASON: UNIT