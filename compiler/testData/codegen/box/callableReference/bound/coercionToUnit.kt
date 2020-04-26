// IGNORE_BACKEND_FIR: JVM_IR
// KJS_WITH_FULL_RUNTIME
// !LANGUAGE: +NewInference
// WITH_RUNTIME

class Foo

class Builder {
    var size: Int = 0

    fun addFoo(foo: Foo): Builder {
        size++
        return this
    }
}

fun box(): String {
    val b = Builder()
    listOf(Foo(), Foo(), Foo()).forEach(b::addFoo)
    return if (b.size == 3) "OK" else "Fail"
}

// DONT_TARGET_EXACT_BACKEND: WASM
//DONT_TARGET_WASM_REASON: BINDING_RECEIVERS