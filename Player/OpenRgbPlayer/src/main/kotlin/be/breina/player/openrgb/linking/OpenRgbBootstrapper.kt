package be.breina.player.openrgb.linking

import be.breina.player.openrgb.MainLoop
import be.breina.player.openrgb.Setup
import be.breina.player.openrgb.animation.Animator
import be.breina.player.Mixer
import be.breina.player.model.SongTheming
import io.gitlab.mguimard.openrgb.client.OpenRGBClient

class OpenRgbBootstrapper(host: String?, port: Int, songTheming: SongTheming, setup: Setup) {
    private val mixer: Mixer
    private val mainLoop: be.breina.player.openrgb.MainLoop

    init {
        val client = OpenRGBClient(host, port, "RGB Light-show")
        client.connect()

        val linker = OpenRgbDeviceLinker(client)
        setup.setupDevices(linker)
        val devices = linker.getLinkedDevices()

        val animator = Animator(songTheming.palette, devices)
        mixer = setup.setupAnimations(animator)
        mainLoop = be.breina.player.openrgb.MainLoop(animator, devices, client)
    }

    fun getMixer(): Mixer = mixer

    fun getMainLoop(): be.breina.player.openrgb.MainLoop = mainLoop
}