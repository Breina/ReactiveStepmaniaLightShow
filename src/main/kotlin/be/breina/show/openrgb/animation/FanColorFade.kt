package be.breina.show.openrgb.animation

import be.breina.show.openrgb.devices.InnerOuterCircle
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import java.time.Duration

class FanColorFade(fan: InnerOuterCircle, duration: Duration, color: OpenRGBColor, inward: Boolean) : AbstractAnimation(duration, fan,
    { progress: Float ->
        when {
            inward -> {
                inward(fan, color, progress)
            }
            else -> {
                outward(fan, color, progress)
            }
        }
    }
) {
    companion object {
        private fun inward(device: InnerOuterCircle, color: OpenRGBColor, progress: Float) {
            device.mergeInnerCircle(ColorUtil.dim(color, 1f - progress))
            device.mergeOuterCircle(ColorUtil.dim(color, (1f - progress) / 2f))
        }

        private fun outward(device: InnerOuterCircle, color: OpenRGBColor, progress: Float) {
            device.mergeInnerCircle(ColorUtil.dim(color, (1f - progress) / 2f))
            device.mergeOuterCircle(ColorUtil.dim(color, 1f - progress))
        }
    }
}