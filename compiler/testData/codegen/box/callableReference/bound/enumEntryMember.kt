// IGNORE_BACKEND: JS, JS_IR
enum class E {
    A, B;

    fun foo() = this.name
}

fun box(): String {
    val f = E.A::foo
    val ef = E::foo

    if (f() != "A") return "Fail 1: ${f()}"
    if (f == E.B::foo) return "Fail 2"
    if (ef != E::foo) return "Fail 3"

    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: IGNORED_IN_JS

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: BINDING_RECEIVERS
