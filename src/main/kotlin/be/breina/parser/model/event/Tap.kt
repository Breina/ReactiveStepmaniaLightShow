package be.breina.parser.model.event

import be.breina.parser.mixer.Mixer

class Tap constructor(time: Long, private val lane: Int) : AbstractEvent(time) {
    override fun visit(mixer: Mixer) {
        mixer.tap(lane)
    }
}