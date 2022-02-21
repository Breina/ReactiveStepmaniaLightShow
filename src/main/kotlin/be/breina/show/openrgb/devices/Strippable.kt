package be.breina.show.openrgb.devices

import io.gitlab.mguimard.openrgb.entity.OpenRGBColor

interface Strippable {
    fun length(): Int
    fun mergeStripLed(index: Int, color: OpenRGBColor)
}