package be.breina.player.openrgb.animation

import be.breina.player.openrgb.devices.InnerOuterFrontBackCircle
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import java.time.Duration

class FanQlColorFade(fan: InnerOuterFrontBackCircle, duration: Duration, color: OpenRGBColor, inward: Boolean) : AbstractAnimation(duration, fan,
    { progress: Float ->
        when {
            inward -> {
                front(fan, color, progress)
            }
            else -> {
                back(fan, color, progress)
            }
        }
    }
) {
    companion object {
        private fun front(device: InnerOuterFrontBackCircle, color: OpenRGBColor, progress: Float) {
            device.mergeInnerFrontCircle(ColorUtil.dim(color, 1f - progress))
            device.mergeOuterFrontCircle(ColorUtil.dim(color, 1f - progress))
        }

        private fun back(device: InnerOuterFrontBackCircle, color: OpenRGBColor, progress: Float) {
            device.mergeInnerRearCircle(ColorUtil.dim(color, 1f - progress))
            device.mergeOuterRearCircle(ColorUtil.dim(color, 1f - progress))
        }
    }
}