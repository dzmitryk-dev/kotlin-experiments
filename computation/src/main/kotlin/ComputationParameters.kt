import java.awt.Color

data class ComputationParameters(
    /**
     * image width
     */
    val width: Int,
    val height: Int,
    /**
     * The minimum x-value of the area in which the Mandelbrot
     * set should be computed
     */
    val x0: Float = -2f,
    /**
     * The minimum y-value of the area in which the Mandelbrot
     * set should be computed
     */
    val y0: Float = -1.3f,

    /**
     * The maximum x-value of the area in which the Mandelbrot
     * set should be computed
     */
    val x1: Float = 0.6f,

    /**
     * The maximum y-value of the area in which the Mandelbrot
     * set should be computed
     */
    val y1: Float = 1.3f,
    val colorsMap: IntArray = initColorMap(32, Color.RED, Color.GREEN, Color.BLUE)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ComputationParameters

        if (width != other.width) return false
        if (height != other.height) return false
        if (x0 != other.x0) return false
        if (y0 != other.y0) return false
        if (x1 != other.x1) return false
        if (y1 != other.y1) return false
        if (!colorsMap.contentEquals(other.colorsMap)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        result = 31 * result + x0.hashCode()
        result = 31 * result + y0.hashCode()
        result = 31 * result + x1.hashCode()
        result = 31 * result + y1.hashCode()
        result = 31 * result + colorsMap.contentHashCode()
        return result
    }
}

/**
 * Creates the colorMap array which contains RGB colors as integers,
 * interpolated through the given colors with colors.length * stepSize
 * steps
 *
 * @param stepSize The number of interpolation steps between two colors
 * @param colors The colors for the map
 */
fun initColorMap(stepSize: Int, vararg colors: Color): IntArray {
    return buildList(stepSize * colors.size) {
        for (i in 0 until colors.size - 1) {
            val c0 = colors[i]
            val r0 = c0.red
            val g0 = c0.green
            val b0 = c0.blue

            val c1 = colors[i + 1]
            val r1 = c1.red
            val g1 = c1.green
            val b1 = c1.blue

            val dr = r1 - r0
            val dg = g1 - g0
            val db = b1 - b0

            for (j in 0 until stepSize) {
                val alpha = j.toFloat() / (stepSize - 1)
                val r = (r0 + alpha * dr).toInt()
                val g = (g0 + alpha * dg).toInt()
                val b = (b0 + alpha * db).toInt()
                val rgb =
                    (r shl 16) or
                            (g shl 8) or
                            (b shl 0)
                add(rgb)
            }
        }
    }.toTypedArray().toIntArray()
}