package be.breina.player.openrgb.devices

import io.gitlab.mguimard.openrgb.entity.OpenRGBColor

interface InnerOuterCircle : RgbDevice {
    fun mergeInnerFrontCircle(openRGBColor: OpenRGBColor)
    fun mergeOuterFrontCircle(openRGBColor: OpenRGBColor)
}