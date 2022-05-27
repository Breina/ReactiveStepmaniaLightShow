package be.breina.common.color.model

import java.util.*

class Region {
    private var isEmptyCallback: Runnable? = null

    val pixels: MedianHeap<Pixel> = MedianHeap()
    fun addPixel(pixel: Pixel) {
        pixels.add(pixel)
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

    fun getMedianPixel(): Pixel = pixels.median

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

    fun isEmpty(): Boolean = pixels.isEmpty()
}