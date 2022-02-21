package be.breina.parser.model.event

import be.breina.parser.mixer.Mixer

class TempoChange(time: Long, private val newTempo: Int) : AbstractEvent(time) {

    override fun visit(mixer: Mixer) {
        mixer.tempo(newTempo)
    }
}