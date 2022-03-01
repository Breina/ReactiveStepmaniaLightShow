package be.breina.color

import org.junit.jupiter.api.Test
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Point
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.stream.IntStream.range
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants

internal class PaletteExtractorTest {
    @Test
    @Throws(IOException::class)
    fun testStScarhand() {
        val bufferedImage = ImageIO.read(
            File(
//                "F:\\Games\\Etterna\\Songs\\Hard Songs Megapack Volume 1\\St. Scarhand\\can-tama119123.png"
                "F:\\Games\\Etterna\\Songs\\Hard Songs Megapack Volume 1\\St. Scarhand\\can-tama119.jpg"
            )
        )
        showImage(bufferedImage)

        val reducedImageData = PaletteExtractor.getReducedImage(bufferedImage)

        val reducedImage = BufferedImage(bufferedImage.width, bufferedImage.height, bufferedImage.type)

        for (x in 0 until reducedImage.width) {
            for (y in 0 until reducedImage.height) {
                reducedImage.setRGB(x, y, reducedImageData.getPixel(x, y).region.getMedianPixel().rgb)
            }
        }

        showImage(reducedImage).location = Point(reducedImage.width, 0)

        val regions = reducedImageData.regions
            .filter { region -> region.pixels.isNotEmpty() }
            .sortedByDescending { region -> region.pixels.size }.toTypedArray()

        val paletteImage = BufferedImage(bufferedImage.width, 100, bufferedImage.type)

        for (i in regions.indices) {
            val region = regions[i]
            val medianPixel = region.getMedianPixel()

            val partWidth = bufferedImage.width / regions.size
            for (x in range(partWidth * i, partWidth * (i + 1))) {
                for (y in range(0, 100)) {
                    paletteImage.setRGB(x, y, medianPixel.rgb)
                }
            }
        }
        val paletteFrame = showImage(paletteImage)
        paletteFrame.location = Point(reducedImage.width, bufferedImage.height + 30)

        Thread.sleep(100000)
    }

    private fun showImage(image: BufferedImage): JFrame {
        val frame = JFrame()
        frame.setSize(image.width, image.height)
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.contentPane = object : JPanel() {
            init {
                preferredSize = Dimension(image.width, image.height)
            }

            override fun paintComponent(g: Graphics) {
                super.paintComponent(g)
                g.drawImage(image, 0, 0, this)
            }
        }
        frame.pack()
        frame.isVisible = true
        return frame
    }
}