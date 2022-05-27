package be.breina.common.dto.event

class PauseEventDTO(time: Long, val duration: Int) : AbstractEventDTO(time)