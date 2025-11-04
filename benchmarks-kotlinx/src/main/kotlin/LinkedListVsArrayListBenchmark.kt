package collection.processing

import kotlinx.benchmark.State
import kotlinx.benchmark.Scope
import kotlinx.benchmark.BenchmarkMode
import kotlinx.benchmark.Mode
import kotlinx.benchmark.OutputTimeUnit
import kotlinx.benchmark.Warmup
import kotlinx.benchmark.Measurement
import kotlinx.benchmark.Param
import kotlinx.benchmark.Setup
import kotlinx.benchmark.Benchmark
import org.openjdk.jmh.annotations.Fork
import java.util.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
open class LinkedListVsArrayListBenchmark {

    @Param("100", "1000", "10000")
    var size: Int = 0

    private lateinit var arrayList: ArrayList<Int>
    private lateinit var linkedList: LinkedList<Int>
    private lateinit var testData: List<Int>

    @Setup
    fun setup() {
        testData = (1..size).toList()
        arrayList = ArrayList()
        linkedList = LinkedList()

        // Pre-populate collections for get/iterate benchmarks
        testData.forEach { value ->
            arrayList.add(value)
            linkedList.add(value)
        }
    }

    // Adding elements benchmarks
    @Benchmark
    fun arrayListAdd(): ArrayList<Int> {
        val list = ArrayList<Int>()
        testData.forEach { list.add(it) }
        return list
    }

    @Benchmark
    fun linkedListAdd(): LinkedList<Int> {
        val list = LinkedList<Int>()
        testData.forEach { list.add(it) }
        return list
    }

    @Benchmark
    fun arrayListAddAtBeginning(): ArrayList<Int> {
        val list = ArrayList<Int>()
        testData.forEach { list.add(0, it) }
        return list
    }

    @Benchmark
    fun linkedListAddAtBeginning(): LinkedList<Int> {
        val list = LinkedList<Int>()
        testData.forEach { list.addFirst(it) }
        return list
    }

    // Getting elements by index benchmarks
    @Benchmark
    fun arrayListRandomAccess(): Int {
        var sum = 0
        repeat(100) {
            val randomIndex = (0 until size).random()
            sum += arrayList[randomIndex]
        }
        return sum
    }

    @Benchmark
    fun linkedListRandomAccess(): Int {
        var sum = 0
        repeat(100) {
            val randomIndex = (0 until size).random()
            sum += linkedList[randomIndex]
        }
        return sum
    }

    @Benchmark
    fun arrayListSequentialAccess(): Int {
        var sum = 0
        for (i in 0 until size) {
            sum += arrayList[i]
        }
        return sum
    }

    @Benchmark
    fun linkedListSequentialAccess(): Int {
        var sum = 0
        for (i in 0 until size) {
            sum += linkedList[i]
        }
        return sum
    }

    // Iteration benchmarks
    @Benchmark
    fun arrayListIteration(): Int {
        var sum = 0
        for (element in arrayList) {
            sum += element
        }
        return sum
    }

    @Benchmark
    fun linkedListIteration(): Int {
        var sum = 0
        for (element in linkedList) {
            sum += element
        }
        return sum
    }

    // First/Last element access
    @Benchmark
    fun arrayListFirstElement(): Int {
        return arrayList.first()
    }

    @Benchmark
    fun linkedListFirstElement(): Int {
        return linkedList.first()
    }

    @Benchmark
    fun arrayListLastElement(): Int {
        return arrayList.last()
    }

    @Benchmark
    fun linkedListLastElement(): Int {
        return linkedList.last()
    }

    // Remove operations
    @Benchmark
    fun arrayListRemoveFirst(): ArrayList<Int> {
        val list = ArrayList(arrayList)
        repeat(minOf(10, size)) {
            if (list.isNotEmpty()) {
                list.removeAt(0)
            }
        }
        return list
    }

    @Benchmark
    fun linkedListRemoveFirst(): LinkedList<Int> {
        val list = LinkedList(linkedList)
        repeat(minOf(10, size)) {
            if (list.isNotEmpty()) {
                list.removeFirst()
            }
        }
        return list
    }

    @Benchmark
    fun arrayListRemoveLast(): ArrayList<Int> {
        val list = ArrayList(arrayList)
        repeat(minOf(10, size)) {
            if (list.isNotEmpty()) {
                list.removeAt(list.size - 1)
            }
        }
        return list
    }

    @Benchmark
    fun linkedListRemoveLast(): LinkedList<Int> {
        val list = LinkedList(linkedList)
        repeat(minOf(10, size)) {
            if (list.isNotEmpty()) {
                list.removeLast()
            }
        }
        return list
    }
}