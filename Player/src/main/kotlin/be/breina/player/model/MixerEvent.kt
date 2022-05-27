package be.breina.player.model

import be.breina.player.Mixer
import java.util.function.Consumer

class MixerEvent(val time: Long, private val executor: Consumer<Mixer>) {
    fun visit(mixer: Mixer) {
        executor.accept(mixer)
    }
}