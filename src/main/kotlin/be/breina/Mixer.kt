package be.breina.parser.mixer

interface Mixer {

    fun tap(index: Int)

    fun jump(index: Int)

    fun hands(index: Int)

    fun quad()

    fun hold(index: Int)

    fun release(index: Int)

    fun roll(index: Int)

    fun mine(index: Int)

    fun tempo(tempo: Int)

    fun stop(duration: Int)
}