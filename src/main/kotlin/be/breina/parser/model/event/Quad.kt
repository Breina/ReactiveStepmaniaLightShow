package be.breina.parser.model.event

import be.breina.parser.mixer.Mixer

class Quad constructor(time: Long) : AbstractEvent(time) {
    override fun visit(mixer: Mixer) {
        mixer.quad()
    }
}