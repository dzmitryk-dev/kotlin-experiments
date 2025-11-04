package dzkoirn

import kotlin.time.measureTimedValue

fun main() {
    println("Hello World!")
    executeErastophenes()
    executeStreams()
}

fun executeErastophenes() {
    val n = 1000000000
//    val n = 1000000
//    val n = 100
    println("executeErastophenes with n = $n")

//    val (output0, m0) = measureTimedValue {
//        primesStupidLoop(n)
//    }
//    println("primesStupidLoop took $m0")

    val (output1, m1) = measureTimedValue {
        sieveOfEratosthenesFromWiki(n)
    }
    println("sieveOfEratosthenesFromWiki took $m1")

    val (output2, m2) = measureTimedValue {
        sieveOfEratosthenesModified(n)
    }
    println("sieveOfEratosthenesModified took $m2")

    val (output3, m3) = measureTimedValue {
        sieveOfEratosthenesFold(n)
    }
    println("sieveOfEratosthenesFold took $m3")

//    println("primesStupidLoop results           : $output2")
//    println("sieveOfEratosthenesFromWiki results: $output1")
//    println("sieveOfEratosthenesModified results: $output2")
//    println("sieveOfEratosthenesFold results    : $output3")
}

fun executeStreams() {
    val n = 1000000

    val (output1, m1) = measureTimedValue {
        sumOfAllSquares(n)
    }
    println("sumOfAllPow took $m1 with result $output1")

    val (output2, m2) = measureTimedValue {
        sumOfAllSquaresStream(n)
    }
    println("sumOfAllPowStream took $m2 with result $output2")

    val (output3, m3) = measureTimedValue {
        sumOfAllEvenSquares(n)
    }
    println("sumOfAllPowStream took $m3 with result $output3")

    val (output4, m4) = measureTimedValue {
        sumOfAllEvenSquaresStream(n)
    }
    println("sumOfAllPowStream took $m4 with result $output4")

    val (output5, m5) = measureTimedValue {
        sumOfAllEvenSquaresStreamBoxed(n)
    }
    println("sumOfAllEvenSquaresStreamBoxed took $m5 with result $output5")

    val (output6, m6) = measureTimedValue {
        sumOfAllEvenSquaresDumpLoop(n)
    }
    println("sumOfAllSquaresDumpLoop took $m6 with result $output6")
}
