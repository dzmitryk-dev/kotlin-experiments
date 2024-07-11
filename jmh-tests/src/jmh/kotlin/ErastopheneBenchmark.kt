package jmh.tests

import dzkoirn.primesStupidLoop
import dzkoirn.sieveOfEratosthenesFold
import dzkoirn.sieveOfEratosthenesFromWiki
import dzkoirn.sieveOfEratosthenesModified
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
open class ErastopheneBenchmark {

    @Param("100", "1000", "10000", "100000", "1000000") var iterations = 0

    @Benchmark
    fun testPrimesStupidLoop(bh: Blackhole) {
        bh.consume(primesStupidLoop(iterations))
    }

    @Benchmark
    fun testSieveOfEratosthenesFromWiki(bh: Blackhole) {
        bh.consume(sieveOfEratosthenesFromWiki(iterations))
    }

    @Benchmark
    fun testSieveOfEratosthenesModified(bh: Blackhole) {
        bh.consume(sieveOfEratosthenesModified(iterations))
    }

    @Benchmark
    fun testSieveOfEratosthenesFold(bh: Blackhole) {
        bh.consume(sieveOfEratosthenesFold(iterations))
    }
}