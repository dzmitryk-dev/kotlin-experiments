package dzkoirn

import java.util.stream.IntStream

fun sumOfAllSquares(n: Int): Long = (1..n).sumOf { it.toLong() * it }

fun sumOfAllSquaresStream(n: Int): Long =
    IntStream.range(1, n).mapToLong { it.toLong() * it }.sum()

fun sumOfAllEvenSquares(n: Int) = (1..n).filter { it % 2 == 0 }.sumOf { it.toLong() * it }

fun sumOfAllEvenSquaresStream(n: Int): Long =
    IntStream.range(1, n).filter { it % 2 == 0  }.mapToLong { it.toLong() * it }.sum()

fun sumOfAllEvenSquaresStreamBoxed(n: Int): Long =
    IntStream.range(1, n).boxed().filter { it % 2 == 0  }.mapToLong { it.toLong() * it }.sum()

fun sumOfAllEvenSquaresDumpLoop(n: Int): Long {
    var sum: Long = 0L
    for (i in 1..n) {
        if (i % 2 == 0) {
            sum += i.toLong() * i
        }
    }
    return sum
}