package be.breina.show.openrgb.devices.impl

import be.breina.show.openrgb.devices.AbstractRgbDevice
import be.breina.show.openrgb.devices.InnerOuterFrontBackCircle
import be.breina.show.openrgb.devices.Strippable
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor

class CorsairQLFan(controllerIndex: Int) : AbstractRgbDevice(controllerIndex, 34), Strippable, InnerOuterFrontBackCircle {
    override fun mergeInnerFrontCircle(openRGBColor: OpenRGBColor) {
        mergeLeds(0, INNER_LENGTH, openRGBColor)
    }

    override fun mergeInnerRearCircle(openRGBColor: OpenRGBColor) {
        mergeLeds(4, INNER_LENGTH, openRGBColor)
    }

    override fun mergeOuterFrontCircle(openRGBColor: OpenRGBColor) {
        mergeLeds(INNER_LENGTH * 2, OUTER_LENGTH, openRGBColor)
    }

    override fun mergeOuterRearCircle(openRGBColor: OpenRGBColor) {
        mergeLeds(INNER_LENGTH * 2 + OUTER_LENGTH, OUTER_LENGTH, openRGBColor)
    }

    override fun length(): Int = OUTER_LENGTH

    override fun mergeStripLed(index: Int, color: OpenRGBColor) {
        mergeLeds(index + INNER_LENGTH, 1, color)
    }

    companion object {
        const val INNER_LENGTH = 4
        const val OUTER_LENGTH = 13
    }
}