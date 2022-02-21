package be.breina.show.openrgb.animation

import be.breina.show.openrgb.devices.RgbDevice
import be.breina.show.openrgb.devices.SingleLed
import be.breina.show.openrgb.devices.Strippable
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import java.time.Duration

open class ColorFade : AbstractAnimation {
    constructor(device: SingleLed, color: OpenRGBColor, duration: Duration) : super(
        duration,
        { progress: Float -> apply(device, color, progress) }
    )

    constructor(device: RgbDevice, color: OpenRGBColor, duration: Duration) : super(
        duration,
        { progress: Float -> apply(device, color, progress) }
    )

    constructor(device: RgbDevice, index: Int, color: OpenRGBColor, duration: Duration) : super(
        duration,
        { progress: Float -> apply(device, index, color, progress) }
    )

//    constructor(strip: Strippable, index: Int, color: OpenRGBColor, duration: Duration) : super(
//        duration,
//        { progress: Float -> apply(strip, index, color, progress) }
//    )

    companion object {
        private fun apply(device: SingleLed, color: OpenRGBColor, progress: Float) {
            device.mergeLed(ColorUtil.dim(color, 1f - progress))
        }

        private fun apply(device: RgbDevice, index: Int, color: OpenRGBColor, progress: Float) {
            device.mergeLeds(index, 1, ColorUtil.dim(color, 1f - progress))
        }

        private fun apply(device: RgbDevice, color: OpenRGBColor, progress: Float) {
            device.mergeLeds(ColorUtil.dim(color, 1f - progress))
        }

        private fun apply(strip: Strippable, index: Int, color: OpenRGBColor, progress: Float) {
            strip.mergeStripLed(index, ColorUtil.dim(color, 1f - progress))
        }
    }
}