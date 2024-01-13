import org.apache.commons.cli.*
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.awt.image.DataBufferInt

import java.io.File
import javax.imageio.ImageIO
import kotlin.time.measureTime

private const val OPTION_OUTPUT = "o"

fun main(args: Array<String>) {
    val options = Options().apply {
        addOption(
            Option.builder(OPTION_OUTPUT)
                .longOpt("output")
                .argName("file")
                .desc("output file")
                .numberOfArgs(1)
                .hasArg()
                .required()
                .build()
        )
    }

    val computationParameters = ComputationParameters(5000, 5000)

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
    val time = measureTime {
        val fileName = "$name.png"

        // Create and fill the memory object containing the color map
        val image = BufferedImage(computationParameters.width, computationParameters.height, TYPE_INT_RGB)

        computationFunction(image.raster.dataBuffer as DataBufferInt, computationParameters)

        File(fileName).outputStream().use { outputStream ->
            ImageIO.write(image, "png", outputStream)
        }

        println("Wrote image to $fileName")
    }
    println("$name finished. Execution took $time")
}

