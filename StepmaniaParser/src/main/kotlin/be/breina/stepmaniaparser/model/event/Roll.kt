package be.breina.stepmaniaparser.model.event

class Roll constructor(time: Long, val lane: Int) : AbstractEvent(time) {
}