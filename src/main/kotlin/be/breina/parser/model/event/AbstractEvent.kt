package be.breina.parser.model.event

import be.breina.parser.model.Event

abstract class AbstractEvent protected constructor(private val time: Long) : Event {
    override fun time(): Long = time
}