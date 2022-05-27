package be.breina.common.dto

import be.breina.common.dto.event.AbstractEventDTO

data class ChartDTO(val difficulty: String, val meter: String, val events: List<AbstractEventDTO>)