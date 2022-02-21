package be.breina.show.openrgb

import be.breina.parser.mixer.Mixer
import be.breina.show.openrgb.OpenRgbException
import be.breina.show.openrgb.animation.Animator
import be.breina.show.openrgb.linking.OpenRgbDeviceLinker

interface Setup {
    @Throws(OpenRgbException::class)
    fun setupDevices(linker: OpenRgbDeviceLinker)
    fun setupAnimations(animator: Animator): Mixer
}