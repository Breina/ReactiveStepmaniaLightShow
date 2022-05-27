package be.breina.player.openrgb.animation

import be.breina.player.openrgb.devices.Strippable
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import java.time.Duration
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow

class InOutWave(strip: Strippable, color: OpenRGBColor, duration: Duration) :
    AbstractAnimation(duration, strip, { progress: Float -> apply(strip, color, progress) }) {
    companion object {
        private fun apply(strip: Strippable, color: OpenRGBColor, progress: Float) {
            val actualCenter = (strip.length() - 1) / 2f
            val lowerCenterLedIndex = floor(actualCenter.toDouble()).toInt()
            val upperCenterLedIndex = ceil(actualCenter.toDouble()).toInt()

            val totalSteps = lowerCenterLedIndex + 1
            val expectedIndex = progress * totalSteps

            val innerIndexOffset = floor(expectedIndex.toDouble()).toInt()
            val outerIndexOffset = ceil(expectedIndex.toDouble()).toInt()
            val innerBrightness = (1f - (expectedIndex - innerIndexOffset)) * (1f - progress).pow(2)
            val outerBrightness = (1f - (outerIndexOffset - expectedIndex)) * (1f - progress).pow(2)

            val innerColor = ColorUtil.dim(color, innerBrightness)
            val outerColor = ColorUtil.dim(color, outerBrightness)

            val innerLowerIndex = lowerCenterLedIndex - innerIndexOffset
            strip.mergeStripLed(innerLowerIndex, innerColor)
            val innerUpperIndex = upperCenterLedIndex + innerIndexOffset
            if (innerLowerIndex != innerUpperIndex) {
                strip.mergeStripLed(innerUpperIndex, innerColor)
            }
            if (outerIndexOffset != totalSteps) {
                strip.mergeStripLed(lowerCenterLedIndex - outerIndexOffset, outerColor)
                strip.mergeStripLed(upperCenterLedIndex + outerIndexOffset, outerColor)
            }
        }
    }
}