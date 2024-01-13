import org.apache.commons.cli.*
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.awt.image.DataBufferInt
import java.io.File
import javax.imageio.ImageIO
import kotlin.time.measureTime

private const val OPTION_OUTPUT = "o"

fun main(args: Array<String>) {
    println("Started")
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

    val time = measureTime {
        try {
            val commandLine = DefaultParser().parse(options, args)

            val fileName = commandLine.getOptionValue(OPTION_OUTPUT)

            val image = generateImage()

            File(fileName).outputStream().use { outputStream ->
                ImageIO.write(image, "png", outputStream)
            }

            println("Wrote image to $fileName")
        } catch (ex: ParseException) {
            HelpFormatter().printHelp("MainKt -o outfile.png", options)
        }
    }
    println("Finished. Execution took $time")
}




private fun generateImage(width: Int = 10000, height: Int = 10000): BufferedImage {
    // Create and fill the memory object containing the color map
    val image = BufferedImage(width, height, TYPE_INT_RGB)

    val computationParameters = ComputationParameters(width, height)

    JOCLSimpleMandelbrot(dataBuffer = image.raster.dataBuffer as DataBufferInt, computationParameters)

    return image
}

