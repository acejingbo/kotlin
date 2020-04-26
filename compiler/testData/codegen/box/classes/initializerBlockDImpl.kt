// KJS_WITH_FULL_RUNTIME
// WITH_RUNTIME
class World() {
  public val items: ArrayList<Item> = ArrayList<Item>()

  inner class Item() {
    init {
      items.add(this)
    }
  }

  val foo = Item()
}

fun box() : String {
  val w = World()
  if (w.items.size != 1) return "fail"
  return "OK"
}



// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: STDLIB_ARRAY_LIST
