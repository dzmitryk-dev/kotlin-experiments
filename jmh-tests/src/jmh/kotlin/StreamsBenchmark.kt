package jmh.tests

import dzkoirn.*
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
open class StreamsBenchmark {

    @Param("100", "1000", "10000", "100000", "1000000") var iterations = 0

    @Benchmark
    fun testSumOfAllPow(bh: Blackhole) {
        bh.consume(sumOfAllSquares(iterations))
    }

    @Benchmark
    fun testSumOfAllPowStream(bh: Blackhole) {
        bh.consume(sumOfAllSquaresStream(iterations))
    }

    @Benchmark
    fun testSumOfAllEvenSqueres(bh: Blackhole) {
        bh.consume(sumOfAllEvenSquares(iterations))
    }

    @Benchmark
    fun testSumOfAllEvenSqueresStream(bh: Blackhole) {
        bh.consume(sumOfAllEvenSquaresStream(iterations))
    }

    @Benchmark
    fun testSumOfAllEvenSquaresStreamBoxed(bh: Blackhole) {
        bh.consume(sumOfAllEvenSquaresStreamBoxed(iterations))
    }

    @Benchmark
    fun testSumOfAllEvenSquaresDumpLoop(bh: Blackhole) {
        bh.consume(sumOfAllEvenSquaresDumpLoop(iterations))
    }
}