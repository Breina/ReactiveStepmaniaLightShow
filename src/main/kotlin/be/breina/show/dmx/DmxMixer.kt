package be.breina.show.dmx

import be.breina.parser.model.Song
import be.breina.show.dmx.animation.Animator
import be.breina.show.dmx.lights.LightCwWw
import be.breina.show.dmx.lights.LightRgbw
import be.breina.show.dmx.lights.LightRgbww
import ch.bildspur.artnet.ArtNetClient
import java.net.InetAddress

class DmxMixer(song: Song) {

    val animator: Animator

    init {
        val dmxData = ByteArray(512)
        val address = InetAddress.getByName("192.168.1.15")

        val client = ArtNetClient()
        client.start()
        animator = Animator(song, { client.unicastDmx(address, 0, 0, dmxData) })

        val livingZo = LightRgbw(dmxData, 17)
        val livingZw = LightRgbw(dmxData, 25)
        val livingNo = LightRgbw(dmxData, 125)
        val livingNw = LightRgbw(dmxData, 137)

        animator.addTapper(livingZo)
        animator.addTapper(livingZw)
        animator.addTapper(livingNo)
        animator.addTapper(livingNw)

//        animator.addJump(LightRgb(dmxData, 41))
//        animator.addJump(LightRgb(dmxData, 153))
//        animator.addJump(LightRgb(dmxData, 159))
        animator.addJump(LightCwWw(dmxData, 85))
        animator.addJump(LightCwWw(dmxData, 93))
        animator.addJump(LightCwWw(dmxData, 97))
        animator.addJump(LightCwWw(dmxData, 101))
        animator.addJump(LightCwWw(dmxData, 89))
        animator.addJump(LightCwWw(dmxData, 105))

//        animator.addJump(livingZo)
//        animator.addJump(livingZw)
//        animator.addJump(livingNo)

        val keukenEiland = LightRgbww(dmxData, 217)
        val keukenKast = LightRgbww(dmxData, 227)
        val livingZetel = LightRgbww(dmxData, 237)

        animator.addHands(keukenEiland)
        animator.addHands(keukenKast)
        animator.addHands(livingZetel)

//        animator.addJump()
        animator.addQuad(LightCwWw(dmxData, 133))

        animator.start()
    }
}