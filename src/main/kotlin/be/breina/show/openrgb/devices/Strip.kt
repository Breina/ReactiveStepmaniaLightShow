package be.breina.show.openrgb.devices

import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import io.gitlab.mguimard.openrgb.entity.OpenRGBDevice

class Strip(controllerIndex: Int, size: Int) : RgbDevice(controllerIndex, size), Strippable {
    constructor(controllerIndex: Int, openRGBDevice: OpenRGBDevice) : this(controllerIndex, openRGBDevice.leds.size)

    override fun length(): Int = getDeviceLedCount()

    override fun mergeStripLed(index: Int, color: OpenRGBColor) {
        mergeLeds(index, 1, color)
    }
}