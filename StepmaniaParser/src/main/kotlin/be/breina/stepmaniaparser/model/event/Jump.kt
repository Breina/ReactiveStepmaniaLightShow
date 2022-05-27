package be.breina.stepmaniaparser.model.event

class Jump constructor(time: Long, val lane: Int) : AbstractEvent(time) {
}