package be.breina.stepmaniaparser.model.event

import be.breina.stepmaniaparser.model.Event

abstract class AbstractEvent protected constructor(private val time: Long) : Event {
    override fun time(): Long = time
}