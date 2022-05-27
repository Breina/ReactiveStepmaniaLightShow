package be.breina.stepmaniaparser.model.event

class TempoChange(time: Long, val newTempo: Int) : AbstractEvent(time)