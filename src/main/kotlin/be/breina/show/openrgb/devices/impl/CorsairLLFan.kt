package be.breina.show.openrgb.devices.impl

import be.breina.show.openrgb.devices.AbstractRgbDevice
import be.breina.show.openrgb.devices.InnerOuterCircle
import be.breina.show.openrgb.devices.Strippable
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor

class CorsairLLFan(controllerIndex: Int) : AbstractRgbDevice(controllerIndex, 16), Strippable, InnerOuterCircle {
    override fun mergeInnerCircle(openRGBColor: OpenRGBColor) {
        mergeLeds(0, INNER_LENGTH, openRGBColor)
    }

    override fun mergeOuterCircle(openRGBColor: OpenRGBColor) {
        mergeLeds(INNER_LENGTH, OUTER_LENGTH, openRGBColor)
    }

    override fun length(): Int = OUTER_LENGTH

    override fun mergeStripLed(index: Int, color: OpenRGBColor) {
        mergeLeds(index + INNER_LENGTH, 1, color)
    }

    companion object {
        const val INNER_LENGTH = 4
        const val OUTER_LENGTH = 12
    }
}