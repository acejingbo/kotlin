// !LANGUAGE: +NewInference +FunctionalInterfaceConversion +SamConversionPerArgument +SamConversionForKotlinFunctions
// WITH_COROUTINES
// WITH_RUNTIME
// SKIP_DCE_DRIVEN

import helpers.*
import kotlin.coroutines.*

fun interface SuspendRunnable {
    suspend fun invoke()
}

fun run(r: SuspendRunnable) {
    r::invoke.startCoroutine(EmptyContinuation)
}

var result = "initial"

var resumingCallback: () -> Unit = {}

suspend fun bar() {
    // Generate proper state machine
    suspendCoroutine<Unit> { cont ->
        resumingCallback = {
            cont.resume(Unit)
        }
    }

    result = "OK"
}

fun box(): String {
    run(::bar)

    if (result != "initial") return "fail"

    resumingCallback()

    return result
}

// DONT_TARGET_EXACT_BACKEND: WASM
//DONT_TARGET_WASM_REASON: COROUTINES