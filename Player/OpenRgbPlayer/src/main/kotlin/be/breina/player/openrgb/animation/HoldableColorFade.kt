package be.breina.player.openrgb.animation

import be.breina.player.openrgb.devices.AbstractRgbDevice
import be.breina.player.openrgb.devices.impl.SingleLed
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import java.time.Duration
import java.time.Instant

class HoldableColorFade : ColorFade {
    private var isHeld: Boolean

    constructor(device: SingleLed, color: OpenRGBColor, duration: Duration) : super(device, color, duration) {
        isHeld = true
    }

    constructor(device: AbstractRgbDevice, color: OpenRGBColor, duration: Duration) : super(device, color, duration) {
        isHeld = true
    }

    constructor(device: AbstractRgbDevice, index: Int, color: OpenRGBColor, duration: Duration) : super(device, index, color, duration) {
        isHeld = true
    }

    override fun tick() {
        if (isHeld) {
            deviceUpdater.accept(0f)
        } else {
            super.tick()
        }
    }

    fun release() {
        isHeld = false
        setStart(Instant.now())
    }
}