package be.breina.player.openrgb.devices

import io.gitlab.mguimard.openrgb.entity.OpenRGBColor

interface Strippable : RgbDevice {
    fun length(): Int
    fun mergeStripLed(index: Int, color: OpenRGBColor)
}