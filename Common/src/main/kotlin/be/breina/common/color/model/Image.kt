package be.breina.common.color.model

import java.awt.image.BufferedImage
import java.util.*
import java.util.stream.Stream

class Image(image: BufferedImage) {
    private val raster: Array<Array<Pixel?>>
    val width: Int
    val height: Int
    val regions: List<Region>

    init {
        width = image.width
        height = image.height
        raster = Array(width) { arrayOfNulls(height) }
        regions = LinkedList()
        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = Pixel(x, y, image.getRGB(x, y))
                raster[x][y] = pixel

                val region = pixel.region
                regions.add(region)
                region.setEmptyCallback { regions.remove(region) }
            }
        }
    }

    fun getPixel(x: Int, y: Int): Pixel {
        assert(isInBounds(x, y))
        return raster[x][y]!!
    }

    private fun isInBounds(x: Int, y: Int): Boolean = x in 0 until width && y in 0 until height

    fun getNeighbours(pixel: Pixel): Stream<Pixel> {
        val x = pixel.x
        val y = pixel.y
        return Stream.of(
            intArrayOf(x - 1, y - 1),
            intArrayOf(x - 1, y),
            intArrayOf(x - 1, y + 1),
            intArrayOf(x, y - 1),
            intArrayOf(x, y + 1),
            intArrayOf(x + 1, y - 1),
            intArrayOf(x + 1, y),
            intArrayOf(x + 1, y + 1)
        )
            .filter { cords: IntArray -> isInBounds(cords[0], cords[1]) }
            .map { cords: IntArray -> getPixel(cords[0], cords[1]) }
    }
}