package be.breina.common.color.model

import be.breina.common.color.Ciede2000
import be.breina.common.color.ColorConverting
import java.util.*

class Pixel(val x: Int, val y: Int, val rgb: Int) : Comparable<Pixel> {
    private val lab = ColorConverting.toLab(ColorConverting.toXyz(rgb))
    var region = Region()

    init {
        region.addPixel(this)
    }

    fun compare(pixel: Pixel): Double {
        return Ciede2000.calculateDeltaE(
            lab.l.toDouble(), lab.a.toDouble(), lab.b.toDouble(),
            pixel.lab.l.toDouble(), pixel.lab.a.toDouble(), pixel.lab.b
                .toDouble()
        )
    }

    override fun compareTo(other: Pixel): Int {
        return if (compare(other) > 0) {
            1
        } else {
            0
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val pixel = other as Pixel
        return x == pixel.x && y == pixel.y
    }

    override fun hashCode(): Int = Objects.hash(x, y)

    fun mergeRegion(region: Region) {
        if (region == this.region) {
            return
        }
        region.pixels.forEach { regionPixel ->
            run {
                regionPixel.region = this.region
                this.region.addPixel(regionPixel)
            }
        }
    }
}