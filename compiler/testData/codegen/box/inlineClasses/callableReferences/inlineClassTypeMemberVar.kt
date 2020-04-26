// !LANGUAGE: +InlineClasses
// WITH_RUNTIME
inline class Z(val x: Int)

class C(var z: Z)

fun box(): String {
    val ref = C::z

    val x = C(Z(42))

    if (ref.get(x).x != 42) throw AssertionError()

    ref.set(x, Z(1234))
    if (ref.get(x).x != 1234) throw AssertionError()

    return "OK"
}
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: PROPERTY_REFERENCES
