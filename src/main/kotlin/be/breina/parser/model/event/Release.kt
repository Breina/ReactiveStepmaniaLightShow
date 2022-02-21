package be.breina.parser.model.event

import be.breina.parser.mixer.Mixer

class Release constructor(time: Long, private val lane: Int) : AbstractEvent(time) {
    override fun visit(mixer: Mixer) {
        mixer.release(lane)
    }
}