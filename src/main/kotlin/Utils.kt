fun readInput(day: Int, test: Boolean = false): String = object {}.javaClass.getResource("/Day$day/input${if (test) "_test" else ""}.txt")?.readText()!!