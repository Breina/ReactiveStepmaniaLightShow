package be.breina.parser.model.event

import be.breina.parser.mixer.Mixer

class Stop(time: Long, private val duration: Int) : AbstractEvent(time) {

    override fun visit(mixer: Mixer) {
        mixer.stop(duration)
    }
}