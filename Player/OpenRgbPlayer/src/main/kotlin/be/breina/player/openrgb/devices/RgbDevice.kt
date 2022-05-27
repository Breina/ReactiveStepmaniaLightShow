package be.breina.player.openrgb.devices

import io.gitlab.mguimard.openrgb.entity.OpenRGBColor

interface RgbDevice {
    val isDirty: Boolean

    val index: Int

    fun getDeviceLeds(): Array<OpenRGBColor>

    fun getDeviceLedCount(): Int = getDeviceLeds().size

    fun mergeLeds(offset: Int, length: Int, color: OpenRGBColor)

    fun clear()
}