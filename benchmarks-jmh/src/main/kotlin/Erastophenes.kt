package dzkoirn

import kotlin.math.sqrt

fun primesStupidLoop(n: Int): List<Int> {
    val primeNumbers = mutableListOf<Int>()

    for (i in 2..n) {
        if (primeNumbers.all { i % it != 0 }) {
            primeNumbers.add(i)
        }
    }
    return primeNumbers
}

fun sieveOfEratosthenesFromWiki(n: Int): List<Int> {
    val primes = BooleanArray(n + 1) { true }
    val sqrtN = sqrt(n.toDouble()).toInt()

    for (i in 2..sqrtN) {
        if (primes[i]) {
            for (j in i * i..n step i) {
                primes[j] = false
            }
        }
    }

    val primeNumbers = mutableListOf<Int>()
    for (i in 2..n) {
        if (primes[i]) primeNumbers.add(i)
    }

    return primeNumbers
}

fun sieveOfEratosthenesModified(n: Int): List<Int> {
    val primes = BooleanArray(n + 1) { true }
    val sqrtN = sqrt(n.toDouble()).toInt()
    val primeNumbers = mutableListOf<Int>()

    for (i in 2 .. n) {
        if (primes[i]) {
            primeNumbers.add(i)
            if (i < sqrtN) {
                for (j in i * i..n step i) {
                    primes[j] = false
                }
            }
        }
    }

    return primeNumbers
}

fun sieveOfEratosthenesFold(n: Int): List<Int> {
    val sqrtN = sqrt(n.toDouble()).toInt()
    return (2..n)
        .fold(BooleanArray(n + 1) { true }) { acc, i ->
            if (acc[i]) {
                if (i < sqrtN) {
                    (i * i..n).step(i).fold(acc) { acc2, j ->
                        acc2[j] = false
                        acc2
                    }
                }
            }
            acc
        }.drop(2)
        .mapIndexedNotNull { index, v ->
            if (v) index + 2 else null
        }
}