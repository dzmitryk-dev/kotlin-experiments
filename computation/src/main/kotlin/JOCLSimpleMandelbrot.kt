import org.jocl.*
import java.awt.image.DataBufferInt
import java.io.*

/**
 * Sample took from https://github.com/gpu/JOCLSamples that use wrapper under OpenCl Library to compute image of Mandelbrot fractal
 *
 * A function that uses a simple OpenCL kernel to compute the Mandelbrot set and displays it in an image
 */
fun JOCLSimpleMandelbrot(
    /**
     * Buffer of image to modificate
     */
    dataBuffer: DataBufferInt,
    computationParameters: ComputationParameters,
) {
    with(computationParameters) {

        val platformIndex = 0
        val deviceType = CL.CL_DEVICE_TYPE_ALL
        val deviceIndex = 0

        // Enable exceptions and subsequently omit error checks in this sample
        CL.setExceptionsEnabled(true)

        // Obtain the number of platforms
        val numPlatformsArray = IntArray(1)
        CL.clGetPlatformIDs(0, null, numPlatformsArray)
        val numPlatforms = numPlatformsArray[0]

        // Obtain a platform ID
        val platforms = arrayOfNulls<cl_platform_id>(numPlatforms)
        CL.clGetPlatformIDs(platforms.size, platforms, null)
        val platform = platforms[platformIndex]

        // Initialize the context properties
        val contextProperties = cl_context_properties()
        contextProperties.addProperty(CL.CL_CONTEXT_PLATFORM.toLong(), platform)

        // Obtain the number of devices for the platform
        val numDevicesArray = IntArray(1)
        CL.clGetDeviceIDs(platform, deviceType, 0, null, numDevicesArray)
        val numDevices = numDevicesArray[0]

        // Obtain a device ID
        val devices = arrayOfNulls<cl_device_id>(numDevices)
        CL.clGetDeviceIDs(platform, deviceType, numDevices, devices, null)
        val device = devices[deviceIndex]

        // Create a context for the selected device
        val context = CL.clCreateContext(
            contextProperties, 1, arrayOf(device),
            null, null, null
        )

        /*
     * The OpenCL command queue
     */
        // Create a command-queue for the selected device
        val properties = cl_queue_properties()
        val commandQueue = CL.clCreateCommandQueueWithProperties(
            context, device, properties, null
        )

        // Program Setup
        val source = readFromResource("kernels/SimpleMandelbrot.cl")

        // Create the program
        val cpProgram = CL.clCreateProgramWithSource(
            context, 1,
            arrayOf(source), null, null
        )

        // Build the program
        CL.clBuildProgram(cpProgram, 0, null, "-cl-mad-enable", null, null)

        /*
     * The OpenCL kernel which will actually compute the Mandelbrot
     * set and store the pixel data in a CL memory object
     */
        // Create the kernel
        val kernel = CL.clCreateKernel(cpProgram, "computeMandelbrot", null)

        /*
     * The OpenCL memory object which stores the pixel data
     */
        // Create the memory object which will be filled with the
        // pixel data
        val pixelMem = CL.clCreateBuffer(
            context, CL.CL_MEM_WRITE_ONLY,
            (width * height * Sizeof.cl_uint).toLong(), null, null
        )

        /*
     * An OpenCL memory object which stores a nifty color map,
     * encoded as integers combining the RGB components of
     * the colors.
     */
        val colorMapMem = CL.clCreateBuffer(
            context, CL.CL_MEM_READ_WRITE,
            (colorsMap.size * Sizeof.cl_uint).toLong(), null, null
        )
        CL.clEnqueueWriteBuffer(
            commandQueue, colorMapMem, true, 0,
            (colorsMap.size * Sizeof.cl_uint).toLong(), Pointer.to(colorsMap), 0, null, null
        )

        // Set work size and execute the kernel
        val globalWorkSize = LongArray(2)
        globalWorkSize[0] = width.toLong()
        globalWorkSize[1] = height.toLong()

        CL.clSetKernelArg(kernel, 0, Sizeof.cl_mem.toLong(), Pointer.to(pixelMem))
        CL.clSetKernelArg(kernel, 1, Sizeof.cl_uint.toLong(), Pointer.to(intArrayOf(width)))
        CL.clSetKernelArg(kernel, 2, Sizeof.cl_uint.toLong(), Pointer.to(intArrayOf(height)))
        CL.clSetKernelArg(kernel, 3, Sizeof.cl_float.toLong(), Pointer.to(floatArrayOf(x0)))
        CL.clSetKernelArg(kernel, 4, Sizeof.cl_float.toLong(), Pointer.to(floatArrayOf(y0)))
        CL.clSetKernelArg(kernel, 5, Sizeof.cl_float.toLong(), Pointer.to(floatArrayOf(x1)))
        CL.clSetKernelArg(kernel, 6, Sizeof.cl_float.toLong(), Pointer.to(floatArrayOf(y1)))
        CL.clSetKernelArg(kernel, 7, Sizeof.cl_int.toLong(), Pointer.to(intArrayOf(maxIterations)))
        CL.clSetKernelArg(kernel, 8, Sizeof.cl_mem.toLong(), Pointer.to(colorMapMem))
        CL.clSetKernelArg(kernel, 9, Sizeof.cl_int.toLong(), Pointer.to(intArrayOf(colorsMap.size)))

        CL.clEnqueueNDRangeKernel(
            commandQueue, kernel, 2, null,
            globalWorkSize, null, 0, null, null
        )

        val data = dataBuffer.data
        CL.clEnqueueReadBuffer(
            commandQueue, pixelMem, CL.CL_TRUE, 0,
            (Sizeof.cl_int * width * height).toLong(), Pointer.to(data), 0, null, null
        )
    }
}


private fun openResourseAsStream(resourceName: String): InputStream =
    requireNotNull(object {}.javaClass.classLoader.getResourceAsStream(resourceName))

private fun InputStream.asReader() =
    BufferedReader(InputStreamReader(BufferedInputStream(this)))

fun readFromResource(resourceName: String): String =
    openResourseAsStream(resourceName).asReader().use { reader ->
        buildString {
            reader.lines().forEach { line -> appendLine(line) }
        }
    }