package be.breina.color

import be.breina.color.model.Image
import be.breina.color.model.Palette
import be.breina.color.model.Pixel
import be.breina.color.model.Region
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.stream.Collectors
import java.util.stream.IntStream

/**
 * @author https://link.springer.com/content/pdf/10.1007%252F978-3-540-85920-8_91.pdf
 */
object PaletteExtractor {
    private const val TOLERANCE_THRESHOLD = 20.0

    fun extractColors(rgbImage: BufferedImage): Palette {
        val image = getReducedImage(rgbImage)

        return Palette(
            image.regions.stream()
                .map(Region::getMedianPixel)
                .map(Pixel::rgb)
                .map(::Color)
                .collect(Collectors.toList())
                .toTypedArray()
        )
    }

    fun getReducedImage(rgbImage: BufferedImage): Image {
        println("Initial grouping...")
        val image = Image(rgbImage)
        IntStream.range(0, image.width)
            .forEach { x: Int ->
                kotlin.run {
                    (if (x % 2 == 0) IntStream.range(0, image.height) else IntStream.range(image.height, 0))
//                    IntStream.range(0, image.height)
                        .forEach { y: Int ->
                            val pixel = image.getPixel(x, y)
                            pixelToRegion(pixel, image)
                        }
                }
            }

        println("Grouping regions...")
        val regions = image.regions.sortedByDescending { region -> region.pixels.size }.toTypedArray()
        for (i in regions.indices) {
            val biggerRegion = regions[i]
            if (biggerRegion.pixels.isEmpty()) {
                continue
            }
            (i + 1 until regions.size)
                .asSequence()
                .map { regions[it] }
                .filter { smallerRegion -> smallerRegion.pixels.isNotEmpty() }
                .filter { biggerRegion.getMedianPixel().compare(it.getMedianPixel()) < TOLERANCE_THRESHOLD }
                .forEach {
                    it.pixels
                        .forEach { pixel ->
                            run {
                                biggerRegion.pixels.add(pixel)
                                pixel.region = biggerRegion
                            }
                        }
                    it.pixels.clear()
                }
        }

        println("Done!")
        return image
    }

    private fun pixelToRegion(pixel: Pixel, image: Image) {
//        image.getNeighbours(pixel)
//            .map { neighbour: Pixel -> NeighbourRank(neighbour, neighbour.compare(pixel)) }
//            .filter { neighbourRank: NeighbourRank -> neighbourRank.score < TOLERANCE_THRESHOLD }
//            .min(Comparator.comparing(NeighbourRank::score))
//            .ifPresentOrElse({ _ -> println("Found one that is okay") }) { println("Nothing found :(") }


        image.getNeighbours(pixel)
            .map { neighbour: Pixel -> NeighbourRank(neighbour, neighbour.compare(pixel)) }
            .filter { neighbourRank: NeighbourRank -> neighbourRank.score < TOLERANCE_THRESHOLD }
            .min(
                Comparator.comparing(NeighbourRank::score)
                    .thenComparing(Comparator.comparing { neighbour -> neighbour.neighbour.region.pixels.size })
            )
            .ifPresent { bestNeighbour: NeighbourRank -> pixel.mergeRegion(bestNeighbour.neighbour.region) }
    }

    class NeighbourRank(val neighbour: Pixel, val score: Double)
}