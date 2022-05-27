package be.breina.player.openrgb.animation

import io.gitlab.mguimard.openrgb.entity.OpenRGBColor

object ColorUtil {
    fun dim(color: OpenRGBColor, brightness: Float): OpenRGBColor =
        OpenRGBColor(
            (color.red.toUByte().toFloat() * brightness).toInt(),
            (color.green.toUByte().toFloat() * brightness).toInt(),
            (color.blue.toUByte().toFloat() * brightness).toInt()
        )
}