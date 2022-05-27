package be.breina.common.dto.event

class IndexSpecificEventDTO(time: Long, val type: IndexSpecificEventType, val index: Int) : AbstractEventDTO(time)