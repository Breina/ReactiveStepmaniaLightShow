package be.breina.player.dmx.lights

import java.nio.ByteBuffer
import java.nio.ByteOrder.BIG_ENDIAN

class SingleLight(private val dmxData: ByteArray, private val address: Int) {
    private val maxBrightness: Int = (1 shl 16) - 1

    fun setBrightness(ratio: Float) {
        byteBuffer().putShort(((ratio * maxBrightness).toInt().toShort()))
    }

    fun getBrightness(): Float = byteBuffer().short.toFloat() / maxBrightness.toFloat()


    private fun byteBuffer() = ByteBuffer.wrap(dmxData, address - 1, 2).order(BIG_ENDIAN)
}