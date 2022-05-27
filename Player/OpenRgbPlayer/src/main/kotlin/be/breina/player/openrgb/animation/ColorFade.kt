package be.breina.player.openrgb.animation

import be.breina.player.openrgb.devices.AbstractRgbDevice
import be.breina.player.openrgb.devices.impl.SingleLed
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import java.time.Duration

open class ColorFade : AbstractAnimation {
    constructor(device: SingleLed, color: OpenRGBColor, duration: Duration) : super(
        duration, device,
        { progress: Float -> apply(device, color, progress) }
    )

    constructor(device: AbstractRgbDevice, color: OpenRGBColor, duration: Duration) : super(
        duration, device,
        { progress: Float -> apply(device, color, progress) }
    )

    constructor(device: AbstractRgbDevice, index: Int, color: OpenRGBColor, duration: Duration) : super(
        duration, device,
        { progress: Float -> apply(device, index, color, progress) }
    )

    companion object {
        private fun apply(device: SingleLed, color: OpenRGBColor, progress: Float) {
            device.mergeLed(ColorUtil.dim(color, 1f - progress))
        }

        private fun apply(device: AbstractRgbDevice, index: Int, color: OpenRGBColor, progress: Float) {
            device.mergeLeds(index, 1, ColorUtil.dim(color, 1f - progress))
        }

        private fun apply(device: AbstractRgbDevice, color: OpenRGBColor, progress: Float) {
            device.mergeLeds(ColorUtil.dim(color, 1f - progress))
        }
    }
}