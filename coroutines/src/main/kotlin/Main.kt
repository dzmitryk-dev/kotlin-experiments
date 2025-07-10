import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
    consecutive()
    parallel()
}

fun consecutive() = runBlocking {
    println("Start consecutive")
    timeOf("consecutive") {
        launch {
            timeOf("DummyFibonachi") {
                println(dummyFibonachi().take(10).joinToString(", ", "[", "]") { "$it" })
            }
        }
        launch {
            timeOf("DummyFibonachi2") {
                println(dummyFibonachi().take(10).joinToString(", ", "[", "]") { "$it" })
            }
        }
    }
    println("Finish consecutive")
}

fun parallel() = runBlocking {
    println("Start parallel")
    timeOf("parallel") {
        withContext(Dispatchers.Default) {
            launch {
                timeOf("DummyFibonachi") {
                    println(dummyFibonachi().take(10).joinToString(", ", "[", "]") { "$it" })
                }
            }
            launch {
                timeOf("DummyFibonachi2") {
                    println(dummyFibonachi().take(10).joinToString(", ", "[", "]") { "$it" })
                }
            }
        }
    }
    println("Finish parallel")
}

suspend fun timeOf(
    message: String,
    block: suspend () -> Unit
) {
    val start = System.currentTimeMillis()
    block()
    val duration = System.currentTimeMillis() - start
    println("Duration of $message $duration ms")
}