package be.breina.player.openrgb

import be.breina.player.openrgb.animation.Animator
import be.breina.player.openrgb.linking.OpenRgbDeviceLinker
import be.breina.player.Mixer

interface Setup {
    @Throws(OpenRgbException::class)
    fun setupDevices(linker: OpenRgbDeviceLinker)
    fun setupAnimations(animator: Animator): Mixer
}