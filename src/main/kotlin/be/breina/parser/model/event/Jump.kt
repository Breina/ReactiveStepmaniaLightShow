package be.breina.parser.model.event

import be.breina.parser.mixer.Mixer

class Jump constructor(time: Long, private val lane: Int) : AbstractEvent(time) {
    override fun visit(mixer: Mixer) {
        mixer.jump(lane)
    }
}