package be.breina.show.openrgb.devices

import io.gitlab.mguimard.openrgb.entity.OpenRGBColor

interface InnerOuterCircle : RgbDevice {
    fun mergeInnerCircle(openRGBColor: OpenRGBColor)
    fun mergeOuterCircle(openRGBColor: OpenRGBColor)
}