package be.breina.common.dto.event

class GenericEventDTO(time: Long, val type: GenericEventType) : AbstractEventDTO(time)