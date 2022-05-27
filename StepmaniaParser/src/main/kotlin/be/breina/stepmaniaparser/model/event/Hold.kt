package be.breina.stepmaniaparser.model.event

class Hold constructor(time: Long, val lane: Int) : AbstractEvent(time) {
}