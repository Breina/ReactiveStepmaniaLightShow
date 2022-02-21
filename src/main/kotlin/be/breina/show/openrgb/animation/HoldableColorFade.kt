package be.breina.show.openrgb.animation

import be.breina.show.openrgb.devices.RgbDevice
import be.breina.show.openrgb.devices.SingleLed
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import java.time.Duration
import java.time.Instant

class HoldableColorFade : ColorFade {
    private var isHeld: Boolean

    constructor(device: SingleLed, color: OpenRGBColor, duration: Duration) : super(device, color, duration) {
        isHeld = true
    }

    constructor(device: RgbDevice, color: OpenRGBColor, duration: Duration) : super(device, color, duration) {
        isHeld = true
    }

    constructor(device: RgbDevice, index: Int, color: OpenRGBColor, duration: Duration) : super(device, index, color, duration) {
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