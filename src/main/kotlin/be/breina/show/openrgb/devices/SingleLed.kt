package be.breina.show.openrgb.devices

import io.gitlab.mguimard.openrgb.entity.OpenRGBColor

class SingleLed(controllerIndex: Int) : RgbDevice(controllerIndex, 1) {
    fun mergeLed(color: OpenRGBColor) {
        mergeLeds(0, 1, color)
    }
}