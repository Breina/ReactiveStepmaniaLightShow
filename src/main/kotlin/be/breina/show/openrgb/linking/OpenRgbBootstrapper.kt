package be.breina.show.openrgb.linking

import be.breina.color.PaletteExtractor
import be.breina.color.model.Palette
import be.breina.parser.mixer.Mixer
import be.breina.parser.model.Song
import be.breina.show.openrgb.MainLoop
import be.breina.show.openrgb.Setup
import be.breina.show.openrgb.animation.Animator
import io.gitlab.mguimard.openrgb.client.OpenRGBClient
import java.awt.Color
import java.util.*

class OpenRgbBootstrapper(host: String?, port: Int, song: Song, setup: Setup) {
    private val mixer: Mixer
    private val mainLoop: MainLoop

    init {
        val client = OpenRGBClient(host, port, "RGB Light-show")
        client.connect()

        val linker = OpenRgbDeviceLinker(client)
        setup.setupDevices(linker)
        val devices = linker.getLinkedDevices()

        val animator = Animator(getColors(song), devices)
        mixer = setup.setupAnimations(animator)
        mainLoop = MainLoop(animator, devices, client)
    }

    fun getMixer(): Mixer = mixer

    fun getMainLoop(): MainLoop = mainLoop

    companion object {
        private fun getColors(song: Song): Palette {
            if (song.background != null) {
                return PaletteExtractor.extractColors(song.background!!)
            } else if (song.banner != null) {
                return PaletteExtractor.extractColors(song.banner!!)
            } else if (song.cdTitle != null) {
                return PaletteExtractor.extractColors(song.cdTitle!!)
            }
            println("Warning: song has no background, banner or cdTitle. Using random colors...")
            val r = Random()
            return Palette(
                arrayOf(
                    Color(r.nextInt()),
                    Color(r.nextInt()),
                    Color(r.nextInt())
                )
            )
        }
    }
}