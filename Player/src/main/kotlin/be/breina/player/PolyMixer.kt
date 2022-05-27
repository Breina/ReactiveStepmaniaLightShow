package be.breina.player

import java.util.function.Consumer

class PolyMixer(private val mixers: List<Mixer>) : Mixer {
    override fun single(index: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.single(index) })
    }

    override fun double(index: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.double(index) })
    }

    override fun triple(index: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.triple(index) })
    }

    override fun quad() {
        mixers.forEach(Consumer(Mixer::quad))
    }

    override fun longStart(index: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.longStart(index) })
    }

    override fun burstStart(index: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.burstStart(index) })
    }

    override fun longOrBurstEnd(index: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.longOrBurstEnd(index) })
    }

    override fun omit(index: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.omit(index) })
    }

    override fun tempo(tempo: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.tempo(tempo) })
    }

    override fun pause(duration: Int) {
        mixers.forEach(Consumer { mixer: Mixer -> mixer.pause(duration) })
    }
}