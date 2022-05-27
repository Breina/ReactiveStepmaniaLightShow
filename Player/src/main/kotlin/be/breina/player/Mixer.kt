package be.breina.player

interface Mixer {

    fun single(index: Int)

    fun double(index: Int)

    fun triple(index: Int)

    fun quad()

    fun longStart(index: Int)

    fun burstStart(index: Int)

    fun longOrBurstEnd(index: Int)

    fun omit(index: Int)

    fun tempo(tempo: Int)

    fun pause(duration: Int)
}