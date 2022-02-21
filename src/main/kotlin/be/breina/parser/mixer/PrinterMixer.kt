package be.breina.parser.mixer

class PrinterMixer : Mixer {
    override fun tap(index: Int) {
        println("Tap")
    }

    override fun jump(index: Int) {
        println("Jump")
    }

    override fun hands(index: Int) {
        println("Hands")
    }

    override fun quad() {
        println("Quad")
    }

    override fun hold(index: Int) {
        println("hold")
    }

    override fun release(index: Int) {
        println("release")
    }

    override fun roll(index: Int) {
        println("roll")
    }

    override fun mine(index: Int) {
        println("mine")
    }

    override fun tempo(tempo: Int) {
        println("tempo: $tempo")
    }

    override fun stop(stop: Int) {
        println("stop: $stop")
    }
}