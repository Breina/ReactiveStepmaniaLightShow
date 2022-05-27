package be.breina.player.openrgb.animation

import be.breina.player.openrgb.devices.InnerOuterCircle
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
            device.mergeInnerFrontCircle(ColorUtil.dim(color, 1f - progress))
            device.mergeOuterFrontCircle(ColorUtil.dim(color, (1f - progress) / 2f))
        }

        private fun outward(device: InnerOuterCircle, color: OpenRGBColor, progress: Float) {
            device.mergeInnerFrontCircle(ColorUtil.dim(color, (1f - progress) / 2f))
            device.mergeOuterFrontCircle(ColorUtil.dim(color, 1f - progress))
        }
    }
}