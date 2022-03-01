package be.breina.color.model

import java.util.*
import java.util.stream.Collectors

class Region {
    private var isEmptyCallback: Runnable? = null
    val pixels: MutableSet<Pixel> = HashSet()
    fun addPixel(pixel: Pixel) {
        pixels.add(pixel)
    }

    fun removePixel(pixel: Pixel) {
        pixels.remove(pixel)
        if (pixels.isEmpty()) {
            isEmptyCallback!!.run()
        }
    }

    /**
     * Biggest first
     */
    fun compareTo(other: Region): Int = if (pixels.size == other.pixels.size) {
        0
    } else {
        -pixels.size.compareTo(other.pixels.size)
    }

    override fun hashCode(): Int = Objects.hash(pixels)

    fun getMedianPixel(): Pixel {
        return pixels.stream().findAny().get()

//        val sortedPixels = pixels.stream()
//            .sorted { o1, o2 ->
//                {
//                    var comparison = o1.compare(o2)
//                    if (comparison > 0) {
//                        1
//                    } else if (comparison < 0) {
//                        -1
//                    } else {
//                        0
//                    }
//                }
//            }
//            .collect(Collectors.toList())
//
//        return sortedPixels[sortedPixels.size / 2]
    }

    fun setEmptyCallback(callback: Runnable?) {
        isEmptyCallback = callback
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Region

        if (pixels != other.pixels) return false

        return true
    }
}