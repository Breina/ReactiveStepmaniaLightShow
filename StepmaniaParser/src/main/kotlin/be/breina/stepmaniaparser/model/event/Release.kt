package be.breina.stepmaniaparser.model.event

class Release constructor(time: Long, val lane: Int) : AbstractEvent(time) {
}