package be.breina.stepmaniaparser.model

import java.util.*

class Chart(
    var chartType: String,
    var description: String,
    var difficulty: String,
    var meter: String,
    var grooveRadar: IntArray,
    var events: LinkedList<Event> = LinkedList()
)