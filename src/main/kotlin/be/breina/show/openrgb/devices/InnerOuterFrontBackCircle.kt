package be.breina.show.openrgb.devices

import io.gitlab.mguimard.openrgb.entity.OpenRGBColor

interface InnerOuterFrontBackCircle : RgbDevice {
    fun mergeInnerFrontCircle(openRGBColor: OpenRGBColor)
    fun mergeInnerRearCircle(openRGBColor: OpenRGBColor)
    fun mergeOuterFrontCircle(openRGBColor: OpenRGBColor)
    fun mergeOuterRearCircle(openRGBColor: OpenRGBColor)
}