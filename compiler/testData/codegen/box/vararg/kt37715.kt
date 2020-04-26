// !LANGUAGE: +NewInference
// WITH_RUNTIME
// KJS_WITH_FULL_RUNTIME

import kotlin.collections.toList

fun <T: Number> foo(vararg values: T) = values.toList()

fun box(): String {
    val a = foo(1, 4.5)
    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
//DONT_TARGET_WASM_REASON: Wasm stdlib: ArrayList