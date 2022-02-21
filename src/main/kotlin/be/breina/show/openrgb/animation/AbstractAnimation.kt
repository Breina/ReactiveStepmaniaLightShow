package be.breina.show.openrgb.animation

import java.time.Duration
import java.time.Instant
import java.util.function.Consumer

abstract class AbstractAnimation(duration: Duration, deviceUpdater: Consumer<Float>) {
    private val durationNanos: Long
    private var start: Instant
    internal val deviceUpdater: Consumer<Float>
    private var isFinished: Boolean

    init {
        durationNanos = duration.toNanos()
        this.deviceUpdater = deviceUpdater
        start = Instant.now()
        isFinished = false
    }

    open fun tick() {
        val currentDurationNanos = Duration.between(start, Instant.now()).toNanos()
        val progress = currentDurationNanos / durationNanos.toFloat()
        if (progress < 1f) {
            deviceUpdater.accept(progress)
        } else {
            finishAnimation()
        }
    }

    private fun finishAnimation() {
        isFinished = true
    }

    protected fun setStart(start: Instant) {
        this.start = start
    }

    protected fun getDeviceUpdater(): Consumer<Float> = deviceUpdater

    fun isFinished(): Boolean = isFinished
}