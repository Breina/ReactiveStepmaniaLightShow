package be.breina.parser.mixer

import java.util.function.Consumer

class PolyMixer(private val mixers: List<Mixer>) : Mixer {
    override fun tap(index: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.tap(index) })
    }

    override fun jump(index: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.jump(index) })
    }

    override fun hands(index: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.hands(index) })
    }

    override fun quad() {
        mixers.forEach(Consumer { obj: Mixer -> obj.quad() })
    }

    override fun hold(index: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.hold(index) })
    }

    override fun release(index: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.release(index) })
    }

    override fun roll(index: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.roll(index) })
    }

    override fun mine(index: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.mine(index) })
    }

    override fun tempo(tempo: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.tempo(tempo) })
    }

    override fun stop(duration: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.stop(duration) })
    }
}