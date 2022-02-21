package be.breina.parser.util

import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*

object ColorExtractor {
    private const val MIN_COLOR_SEPARATION = 15

    fun extractColors(image: BufferedImage): Palette {
        val bins: MutableMap<Int, Int> = LinkedHashMap()
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val color = (getHue(image.getRGB(x, y)) * 360F).toInt()
                bins.compute(color) { _: Int, count: Int? -> if (count == null) 1 else count + 1 }
            }
        }

//        val (popularHue, count) = bins.entries
//            .sortedBy(MutableMap.MutableEntry<Int, Int>::value)
//            .first()

        val primaryColor = filterColor(bins)
        val secondaryColor = filterColor(bins)
        val tertiaryColor = filterColor(bins)

        return Palette(primaryColor, secondaryColor, tertiaryColor)

//        val colors = bins.entries
//            .sortedByDescending(MutableMap.MutableEntry<Int, Int>::value)
//            .toList()
//
//        val jFrame = JFrame()
//
//        jFrame.add(object : JPanel() {
//            override fun paint(gOld: Graphics) {
//                super.paint(gOld)
//
//                val g = gOld as Graphics2D
//
//                g.background = Color(Color.HSBtoRGB(primaryColor.key / 360F, 1F, 1F))
//                g.clearRect(0, 0, width, height)
//
//
//                for (index in colors.indices) {
//                    val entry = colors[index]
//                    g.color = Color(Color.HSBtoRGB(entry.key / 360F, 1F, 1F))
//                    g.drawLine(0, index, entry.value, index)
//                }
//            }
//        })
//
//        jFrame.setSize(1024, 800)
//        jFrame.defaultCloseOperation = EXIT_ON_CLOSE
//        jFrame.isVisible = true
//
//        println()
    }

    private fun filterColor(bins: MutableMap<Int, Int>): Int {
        val primaryColor = bins.entries.maxByOrNull(MutableMap.MutableEntry<Int, Int>::value)!!.key

        val minColor = primaryColor - MIN_COLOR_SEPARATION
        val maxColor = primaryColor + MIN_COLOR_SEPARATION
        for (i in minColor until maxColor) {
            bins.remove(i % 360)
        }
        return primaryColor
    }

    fun getHue(color: Int): Float {
        val r = color and 0x00FF0000 shr 16
        val g = color and 0x0000FF00 shr 8
        val b = color and 0x000000FF
        var cmax = Math.max(r, g)
        if (b > cmax) {
            cmax = b
        }
        var cmin = Math.min(r, g)
        if (b < cmin) {
            cmin = b
        }
        val redc = (cmax - r).toFloat() / (cmax - cmin).toFloat()
        val greenc = (cmax - g).toFloat() / (cmax - cmin).toFloat()
        val bluec = (cmax - b).toFloat() / (cmax - cmin).toFloat()
        var hue: Float = when {
            r == cmax -> {
                bluec - greenc
            }
            g == cmax -> {
                2.0f + redc - bluec
            }
            else -> {
                4.0f + greenc - redc
            }
        }
        hue /= 6.0f
        if (hue < 0) {
            hue += 1.0f
        }
        return hue
    }

    class Palette(private val primary: Int, private val secondary: Int, private val tertiary: Int) {

        fun primary() = getColor(primary)
        fun secondary() = getColor(secondary)
        fun tertiary() = getColor(tertiary)

        private fun getColor(int: Int) = Color(
            Color.HSBtoRGB(
                (int + rand.nextInt(MIN_COLOR_SEPARATION * 2) - MIN_COLOR_SEPARATION) / 360F, 1F, 1F
            )
        )

        companion object {
            private val rand = Random()
        }
    }
}