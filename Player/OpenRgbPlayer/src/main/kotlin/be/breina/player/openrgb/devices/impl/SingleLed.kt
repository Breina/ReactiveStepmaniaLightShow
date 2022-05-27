package be.breina.player.openrgb.devices.impl

import be.breina.player.openrgb.devices.AbstractRgbDevice
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor

class SingleLed(controllerIndex: Int) : AbstractRgbDevice(controllerIndex, 1) {
    fun mergeLed(color: OpenRGBColor) {
        mergeLeds(0, 1, color)
    }
}