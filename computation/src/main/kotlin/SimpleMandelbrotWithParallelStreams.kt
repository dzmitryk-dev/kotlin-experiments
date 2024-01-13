import java.awt.image.DataBufferInt
import java.util.stream.IntStream

fun SimpleMandelbrotWithParallelStreams(
    dataBuffer: DataBufferInt,
    computationParameters: ComputationParameters
) {
    val output = dataBuffer.data
    with(computationParameters) {
        IntStream.range(0, width).mapToObj { ix ->
            IntStream.range(0, height).mapToObj { iy ->
                intArrayOf(ix, iy)
            }
        }.flatMap { it }
            .parallel()
            .forEach { (ix, iy) ->
                val r = x0 + ix * (x1 - x0) / width
                val i = y0 + iy * (y1 - y0) / height

                var x = 0.0
                var y = 0.0

                var magnitudeSquared = 0.0
                var iteration = 0
                while (iteration < maxIterations && magnitudeSquared < 4) {
                    val xx = x * x
                    val yy = y * y
                    y = 2 * x * y + i
                    x = xx - yy + r
                    magnitudeSquared = xx + yy;
                    iteration++;
                }
                if (iteration == maxIterations) {
                    output[iy * width + ix] = 0
                } else {
                    val alpha = iteration.toFloat() / maxIterations
                    val colorIndex = (alpha * colorsMap.size).toInt()
                    output[iy * width + ix] = colorsMap[colorIndex]
                }
            }
    }
}
