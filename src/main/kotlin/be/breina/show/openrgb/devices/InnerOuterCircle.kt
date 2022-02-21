package be.breina.show.openrgb.devices

import io.gitlab.mguimard.openrgb.entity.OpenRGBColor

interface InnerOuterCircle {
    fun mergeInnerCircle(openRGBColor: OpenRGBColor)
    fun mergeOuterCircle(openRGBColor: OpenRGBColor)
}