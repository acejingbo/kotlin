// !LANGUAGE: +NewInference +FunctionalInterfaceConversion +SamConversionPerArgument +SamConversionForKotlinFunctions
// IGNORE_BACKEND_FIR: JVM_IR
// WITH_RUNTIME
// SKIP_DCE_DRIVEN

fun interface MyRunnable {
    fun invoke()
}

class A {
    inline fun doWork(noinline job: () -> Unit) {
        MyRunnable(job).invoke()
    }

    fun doNoninlineWork(job: () -> Unit) {
        MyRunnable(job).invoke()
    }
}

fun box(): String {
    var result = false
    A().doWork { result = true }
    if (!result) return "Fail 1"

    result = false
    A().doNoninlineWork { result = true }
    if (!result) return "Fail 2"

    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
//DONT_TARGET_WASM_REASON: SAM_CONVERSIONS