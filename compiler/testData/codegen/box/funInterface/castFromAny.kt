// !LANGUAGE: +NewInference +FunctionalInterfaceConversion +SamConversionPerArgument +SamConversionForKotlinFunctions
// IGNORE_BACKEND_FIR: JVM_IR
// SKIP_DCE_DRIVEN


fun interface KRunnable {
    fun invoke()
}

fun test(a: Any?) {
    a as () -> Unit
    KRunnable(a).invoke()
}

fun box(): String {
    var result = "Fail"
    test {
        result = "OK"
    }
    return result
}

// DONT_TARGET_EXACT_BACKEND: WASM
//DONT_TARGET_WASM_REASON: Wasm stdlib: Maps