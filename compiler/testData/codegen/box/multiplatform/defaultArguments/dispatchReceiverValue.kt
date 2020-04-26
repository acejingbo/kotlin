// !LANGUAGE: +MultiPlatformProjects
// IGNORE_BACKEND_FIR: JVM_IR

// FILE: common.kt

expect class C {
    val value: String

    fun test(result: String = value): String
}

// FILE: platform.kt

actual class C(actual val value: String) {
    actual fun test(result: String): String = result
}

fun box() = C("Fail").test("OK")

// DONT_TARGET_EXACT_BACKEND: WASM
//DONT_TARGET_WASM_REASON: EXPECT_DEFAULT_PARAMETERS