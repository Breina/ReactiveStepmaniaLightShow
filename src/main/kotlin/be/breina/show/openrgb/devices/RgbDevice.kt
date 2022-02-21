package be.breina.show.openrgb.devices

import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import java.util.*

abstract class RgbDevice protected constructor(controllerIndex: Int, size: Int) {
    private val deviceLeds: Array<OpenRGBColor>
    val index: Int

    init {
        deviceLeds = Array(size) { OpenRGBColor(0, 0, 0) }
        index = controllerIndex
    }

    fun mergeLeds(color: OpenRGBColor) {
        mergeLeds(0, getDeviceLedCount(), color)
    }

    open fun mergeLeds(offset: Int, length: Int, color: OpenRGBColor) {
        val maxIndex = offset + length
        for (i in offset until maxIndex) {
            val led = deviceLeds[i]
            deviceLeds[i] = OpenRGBColor(
                Integer.max(led.red.toUByte().toInt(), color.red.toUByte().toInt()),
                Integer.max(led.green.toUByte().toInt(), color.green.toUByte().toInt()),
                Integer.max(led.blue.toUByte().toInt(), color.blue.toUByte().toInt())
            )
        }
    }

    open fun clear() {
        Arrays.fill(deviceLeds, BLACK)
    }

    open fun getDeviceLedCount(): Int = deviceLeds.size

    open fun getDeviceLeds(): Array<OpenRGBColor> = deviceLeds

    companion object {
        val BLACK = OpenRGBColor(0, 0, 0)
    }
}