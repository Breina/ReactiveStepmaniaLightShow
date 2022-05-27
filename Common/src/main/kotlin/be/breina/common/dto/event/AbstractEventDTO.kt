package be.breina.common.dto.event

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "eventType"
)
@JsonSubTypes(
    value = [
        Type(name = "genericEvent", value = GenericEventDTO::class),
        Type(name = "laneSpecificEvent", value = IndexSpecificEventDTO::class),
        Type(name = "tempoEvent", value = TempoEventDTO::class),
        Type(name = "pauseEvent", value = PauseEventDTO::class)
    ]
)
open class AbstractEventDTO(val time: Long)