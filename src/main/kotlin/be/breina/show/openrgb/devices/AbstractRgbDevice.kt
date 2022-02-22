package be.breina.show.openrgb.devices

import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import java.util.*

abstract class AbstractRgbDevice protected constructor(controllerIndex: Int, size: Int) : RgbDevice {
    private val deviceLeds: Array<OpenRGBColor>
    final override val index: Int
    override var isDirty = false
//    override var parent: RgbDevice? = null

    init {
        deviceLeds = Array(size) { OpenRGBColor(0, 0, 0) }
        index = controllerIndex
    }

    fun mergeLeds(color: OpenRGBColor) {
        mergeLeds(0, getDeviceLedCount(), color)
    }

    override fun mergeLeds(offset: Int, length: Int, color: OpenRGBColor) {
        val maxIndex = offset + length
        for (i in offset until maxIndex) {
            val led = deviceLeds[i]
            deviceLeds[i] = OpenRGBColor(
                Integer.max(led.red.toUByte().toInt(), color.red.toUByte().toInt()),
                Integer.max(led.green.toUByte().toInt(), color.green.toUByte().toInt()),
                Integer.max(led.blue.toUByte().toInt(), color.blue.toUByte().toInt())
            )
        }
        isDirty = true
    }

    override fun clear() {
        Arrays.fill(deviceLeds, BLACK)
        isDirty = false
    }

    override fun getDeviceLeds(): Array<OpenRGBColor> = deviceLeds

    companion object {
        val BLACK = OpenRGBColor(0, 0, 0)
    }
}