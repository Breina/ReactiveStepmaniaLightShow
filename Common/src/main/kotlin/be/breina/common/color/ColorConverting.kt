package be.breina.common.color

import be.breina.common.color.model.Lab
import be.breina.common.color.model.Xyz
import kotlin.math.pow

/**
 * @author https://home2.htw-berlin.de/~barthel/ImageJ/ColorInspector/HTMLHelp/farbraumJava.htm
 */
object ColorConverting {
    fun toXyz(rgb: Int): Xyz {
        val red255 = rgb shr 16 and 0xFF
        var r = red255 / 255f
        r = if (r <= 0.04045) {
            r / 12
        } else {
            ((r + 0.055) / 1.055).pow(2.4).toFloat()
        }
        val green255 = rgb shr 8 and 0xFF
        var g = green255 / 255f
        g = if (g <= 0.04045) {
            g / 12
        } else {
            ((g + 0.055) / 1.055).pow(2.4).toFloat()
        }
        val blue255 = rgb and 0xFF
        var b = blue255 / 255f
        b = if (b <= 0.04045) {
            b / 12
        } else {
            ((b + 0.055) / 1.055).pow(2.4).toFloat()
        }
        return Xyz(
            0.43605202f * r + 0.3850816f * g + 0.14308742f * b,
            0.22249159f * r + 0.71688604f * g + 0.060621485f * b,
            0.013929122f * r + 0.097097f * g + 0.7141855f * b
        )
    }

    fun toLab(xyz: Xyz): Lab {
        val xr = xyz.x / 0.964221f
        val yr = xyz.y
        val zr = xyz.z / 0.825211f
        val eps = 216f / 24389f
        val k = 24389f / 27f
        val fx: Float = if (xr > eps) {
            xr.toDouble().pow(1 / 3.0).toFloat()
        } else {
            ((k * xr + 16.0) / 116.0).toFloat()
        }
        val fy: Float = if (yr > eps) {
            yr.toDouble().pow(1 / 3.0).toFloat()
        } else {
            ((k * yr + 16.0) / 116.0).toFloat()
        }
        val fz: Float = if (zr > eps) {
            zr.toDouble().pow(1 / 3.0).toFloat()
        } else {
            ((k * zr + 16.0) / 116).toFloat()
        }
        val ls = 116 * fy - 16
        val `as` = 500 * (fx - fy)
        val bs = 200 * (fy - fz)
        return Lab((2.55 * ls + .5).toInt(), (`as` + .5).toInt(), (bs + .5).toInt())
    }
}