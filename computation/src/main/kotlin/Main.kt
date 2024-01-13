import org.apache.commons.cli.*
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.awt.image.DataBufferInt

import java.io.File
import javax.imageio.ImageIO
import kotlin.time.measureTime

fun main() {
    println("Started")
    val computationParameters = ComputationParameters(10000, 10000)

    generateImage("SimpleMandelbrot", computationParameters, ::SimpleMandelbrot)
    generateImage("SimpleMandelbrotWithParallelStreams", computationParameters, ::SimpleMandelbrotWithParallelStreams)
    generateImage("JOCLSimpleMandelbrot", computationParameters, ::JOCLSimpleMandelbrot)

    println("Finished")
}


private fun generateImage(
    name: String,
    computationParameters: ComputationParameters,
    computationFunction: (DataBufferInt, ComputationParameters) -> Unit
) {
    val fileName = "$name.png"
    val time = measureTime {

        // Create and fill the memory object containing the color map
        val image = BufferedImage(computationParameters.width, computationParameters.height, TYPE_INT_RGB)

        computationFunction(image.raster.dataBuffer as DataBufferInt, computationParameters)

        File(fileName).outputStream().use { outputStream ->
            ImageIO.write(image, "png", outputStream)
        }
    }
    println("$name finished. Wrote image to $fileName. Execution took $time")
}

