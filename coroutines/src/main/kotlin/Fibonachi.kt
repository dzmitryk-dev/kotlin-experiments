

fun dummyFibonachi(): Sequence<Long> {
    fun recFib(n: Long): Long =
        when (n) {
            1L -> 1
            2L -> 1
            else -> recFib(n - 1) + recFib(n - 2)
        }
    return generateSequence(1L) { it + 1 }.map { recFib(it) }
}