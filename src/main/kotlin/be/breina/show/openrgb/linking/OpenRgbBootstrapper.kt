package be.breina.show.openrgb.linking

import be.breina.parser.mixer.Mixer
import be.breina.parser.model.Song
import be.breina.parser.util.ColorExtractor
import be.breina.parser.util.ColorExtractor.extractColors
import be.breina.show.openrgb.MainLoop
import be.breina.show.openrgb.Setup
import be.breina.show.openrgb.animation.Animator
import be.breina.show.openrgb.openrgb.DeviceUpdater
import io.gitlab.mguimard.openrgb.client.OpenRGBClient
import java.util.*

class OpenRgbBootstrapper(host: String?, port: Int, song: Song, setup: Setup) {
    private val mixer: Mixer
    private val mainLoop: MainLoop

    init {
        val client = OpenRGBClient(host, port, "RGB Light-show")
        client.connect()

        val linker = OpenRgbDeviceLinker(client)
        setup.setupDevices(linker)
        val deviceUpdater = DeviceUpdater(client, linker.getLinkedDevices())

        val animator = Animator(getColors(song))
        mixer = setup.setupAnimations(animator)
        mainLoop = MainLoop(deviceUpdater, animator)
    }

    fun getMixer(): Mixer = mixer

    fun getMainLoop(): MainLoop = mainLoop

    companion object {
        private fun getColors(song: Song): ColorExtractor.Palette {
            if (song.background != null) {
                return extractColors(song.background!!)
            } else if (song.banner != null) {
                return extractColors(song.banner!!)
            } else if (song.cdTitle != null) {
                return extractColors(song.cdTitle!!)
            }
            println("Warning: song has no background, banner or cdTitle. Using random colors...")
            val r = Random()
            return ColorExtractor.Palette(r.nextInt(), r.nextInt(), r.nextInt())
        }
    }
}