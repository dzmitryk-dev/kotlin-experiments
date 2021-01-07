import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main(args: Array<String>) {
//    consecutive()
    parallel()
}

fun consecutive() = runBlocking {
    timeOf("consecutive") {
        launch {
            timeOf("DummyFibonachi") {
                println(dummyFibonachi().take(50).joinToString(", ", "[", "]") { "$it" })
            }
        }
        launch {
            timeOf("DummyFibonachi2") {
                println(dummyFibonachi().take(50).joinToString(", ", "[", "]") { "$it" })
            }
        }
    }
}

fun parallel() = runBlocking {
    timeOf("consecutive") {
        withContext(Dispatchers.IO) {
            launch {
                timeOf("DummyFibonachi") {
                    println(dummyFibonachi().take(50).joinToString(", ", "[", "]") { "$it" })
                }
            }
            launch {
                timeOf("DummyFibonachi2") {
                    println(dummyFibonachi().take(50).joinToString(", ", "[", "]") { "$it" })
                }
            }
        }
    }
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