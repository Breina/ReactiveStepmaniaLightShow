package be.breina.show.openrgb.animation

import be.breina.parser.util.ColorExtractor
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import java.awt.Color
import java.util.concurrent.CopyOnWriteArrayList

class Animator(private val palette: ColorExtractor.Palette) {
    private val animations: CopyOnWriteArrayList<AbstractAnimation> = CopyOnWriteArrayList()

    fun addAnimations(vararg animations: AbstractAnimation) {
        animations.forEach(this.animations::add)
    }

    fun getPrimaryColor(): OpenRGBColor = mapToOpenRgb(palette.primary())

    fun getSecondaryColor(): OpenRGBColor = mapToOpenRgb(palette.secondary())

    fun getTertiaryColor(): OpenRGBColor = mapToOpenRgb(palette.tertiary())

    @Synchronized
    fun tick() {
        animations.forEach(AbstractAnimation::tick)
        animations.removeIf(AbstractAnimation::isFinished)
    }

    companion object {
        private fun mapToOpenRgb(color: Color): OpenRGBColor = OpenRGBColor(color.red, color.green, color.blue)
    }
}