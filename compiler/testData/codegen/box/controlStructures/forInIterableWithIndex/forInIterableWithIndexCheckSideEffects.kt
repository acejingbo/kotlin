// IGNORE_BACKEND_FIR: JVM_IR
// KJS_WITH_FULL_RUNTIME
// WITH_RUNTIME

class CountingIterable<out T>(private val s: Iterable<T>) : Iterable<T> {
    var hasNextCtr = 0
    var nextCtr = 0

    inner class CountingIterableIterator(private val it: Iterator<T>) : Iterator<T> {
        override fun hasNext() = it.hasNext().also { hasNextCtr++ }
        override fun next() = it.next().also { nextCtr++ }
    }

    override fun iterator() = CountingIterableIterator(s.iterator())
}

val xs = CountingIterable(listOf("a", "b", "c", "d"))

fun box(): String {
    val s = StringBuilder()

    for ((index, x) in xs.withIndex()) {
        s.append("$index:$x;")
    }

    val ss = s.toString()
    if (ss != "0:a;1:b;2:c;3:d;") return "fail: '$ss'"
    if (xs.hasNextCtr != 5) return "hasNextCtr != 5, was: '${xs.hasNextCtr}'"
    if (xs.nextCtr != 4) return "nextCtr != 4, was: '${xs.nextCtr}'"

    return "OK"
}


// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: STDLIB_GENERATED
