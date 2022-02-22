package be.breina.show.openrgb.devices.impl

import be.breina.show.openrgb.devices.AbstractRgbDevice
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor

class SingleLed(controllerIndex: Int) : AbstractRgbDevice(controllerIndex, 1) {
    fun mergeLed(color: OpenRGBColor) {
        mergeLeds(0, 1, color)
    }
}