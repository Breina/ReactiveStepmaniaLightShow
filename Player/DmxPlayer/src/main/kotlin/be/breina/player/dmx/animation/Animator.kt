package be.breina.player.dmx.animation

import be.breina.player.dmx.lights.LightCwWw
import be.breina.player.dmx.lights.LightRgb
import be.breina.player.dmx.lights.LightRgbw
import be.breina.player.dmx.lights.LightRgbww
import be.breina.player.Mixer
import be.breina.player.model.SongTheming
import java.awt.Color
import kotlin.math.pow

class Animator(songTheming: SongTheming, private val send: () -> Unit) : Mixer {
    private var stripIndex: Int = 0
    private var previousTapIndex: Int = 0
    private var timeToStop: Long = 0

    private val tapLightData: ArrayList<LightData> = ArrayList()
    private val jumpLightData: ArrayList<LightData> = ArrayList()
    private val handsLightData: ArrayList<LightData> = ArrayList()
    private val quadLightData: ArrayList<LightData> = ArrayList()


    fun addTapper(light: LightCwWw) {
        tapLightData.add(LightData(light::setColorTemperatureBrightness) { light.colorTemperature = it })
    }

    fun addTapper(light: LightRgbw) {
        tapLightData.add(LightData(light::setColorBrightness) { light.color = it })
    }

    fun addTapper(light: LightRgbww) {
        tapLightData.add(LightData(light::setColorBrightness) { light.color = it })
    }

    fun addJump(light: LightCwWw) {
        jumpLightData.add(LightData(light::setColorTemperatureBrightness) { light.colorTemperature = it })
    }

    fun addJump(light: LightRgb) {
        jumpLightData.add(LightData(light::setColorBrightness) { light.color = it })
    }

    fun addJump(light: LightRgbw) {
        jumpLightData.add(LightData(light::setWhiteBrightness, {}))
    }

    fun addJump(light: LightRgbww) {
        jumpLightData.add(LightData(light::setColorTemperatureBrightness) { light.color = it })
    }

    fun addHands(light: LightRgbww) {
        handsLightData.add(LightData(light::setColorBrightness) { light.color = it })
    }

    fun addQuad(light: LightCwWw) {
        quadLightData.add(LightData(light::setColorTemperatureBrightness) { light.colorTemperature = it })
    }

    fun addQuad(light: LightRgbww) {
        quadLightData.add(LightData(light::setColorTemperatureBrightness) { light.colorTemperature = it })
    }

    private var tempo: Int = 0
        set(value) {
            field = value
            dimRate = 0.0006F * value.toDouble().pow(1.3).toFloat() + 1
        }

    private var dimRate: Float = 1.015F
    private var startTime: Long = System.currentTimeMillis()
    private val palette: be.breina.player.model.Palette = songTheming.palette

    fun start() {
        Thread {
            while (true) {
                if (timeToStop > 0) {
                    timeToStop -= FRAME_DELAY
                    Thread.sleep(FRAME_DELAY)
                    continue
                }

                val circleShrink: (LightData) -> Unit = { circleData ->
                    if (!circleData.held) {
                        circleData.brightness /= dimRate
                    }

                    if (circleData.brightness < 0.1f) {
                        circleData.shouldDraw = false
                    }
                }

                tapLightData.forEach(circleShrink)
                jumpLightData.forEach(circleShrink)
                handsLightData.forEach(circleShrink)
                quadLightData.forEach(circleShrink)

                tapLightData.forEach(LightData::draw)
                jumpLightData.forEach(LightData::draw)
                handsLightData.forEach(LightData::draw)
                quadLightData.forEach(LightData::draw)

                send.invoke()

                Thread.sleep(FRAME_DELAY)
            }
        }.start()
    }

    inner class LightData(private val setBrightness: (Float) -> Unit, private val setColor: (Color) -> Unit) {
        var held: Boolean = false
        var shouldDraw: Boolean = false
        var brightness: Float = 0f

        fun tap(color: Color = palette.primary) {
            shouldDraw = true
            brightness = 1f
            setColor(color)
            held = false
        }

        fun hold(color: Color = palette.secondary) {
            shouldDraw = true
            brightness = 1f
            held = true
            setColor(color)
        }

        fun draw() {
            setBrightness.invoke(brightness)
        }

        private fun setColor(color: Color) {
            setColor.invoke(color)
        }
    }

    companion object {
        private const val FRAME_DELAY = (1000 / 20).toLong()
    }

    override fun single(index: Int) {
        tapLightData[index].tap()
    }

    override fun double(index: Int) {
        jumpLightData[index].tap(palette.secondary)
    }

    override fun triple(index: Int) {
        handsLightData[index % 3].tap(palette.tertiary)
    }

    override fun quad() {
        quadLightData[0].tap(palette.tertiary)
    }

    override fun longStart(index: Int) {
        tapLightData[index].hold()
    }

    override fun burstStart(index: Int) {
        tapLightData[index].hold()
//        TODO("Not yet implemented")
    }

    override fun longOrBurstEnd(index: Int) {
        tapLightData[index].held = false
    }

    override fun omit(index: Int) {
//        TODO("Not yet implemented")
    }

    override fun tempo(tempo: Int) {
        this.tempo = tempo
    }

    override fun pause(duration: Int) {
        timeToStop = duration.toLong()
    }

}