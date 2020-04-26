// KJS_WITH_FULL_RUNTIME
// WITH_RUNTIME

class A

class B(val items: Collection<A>)

class C {
    fun foo(p: Int) {
        when (p) {
            1 -> arrayListOf<Int>().add(1)
        }
    }

    fun bar() = B(listOf<A>().map { it })
}

fun box(): String {
    C().foo(1)
    if (C().bar().items.isNotEmpty()) return "fail"

    return "OK"
}



// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: STDLIB_ARRAY_LIST
