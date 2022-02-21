package be.breina.show.dmx.animation

import be.breina.parser.mixer.Mixer
import be.breina.parser.model.Song
import be.breina.parser.util.ColorExtractor
import be.breina.show.dmx.lights.LightCwWw
import be.breina.show.dmx.lights.LightRgb
import be.breina.show.dmx.lights.LightRgbw
import be.breina.show.dmx.lights.LightRgbww
import java.awt.Color
import java.util.*
import kotlin.math.pow

class Animator(song: Song, val send: () -> Unit) : Mixer {
    private var stripIndex: Int = 0
    private var previousTapIndex: Int = 0
    private var timeToStop: Long = 0

    private val tapLightData: ArrayList<LightData> = ArrayList()
    private val jumpLightData: ArrayList<LightData> = ArrayList()
    private val handsLightData: ArrayList<LightData> = ArrayList()
    private val quadLightData: ArrayList<LightData> = ArrayList()


    fun addTapper(light: LightCwWw) {
        tapLightData.add(LightData(light::setColorTemperatureBrightness, { light.colorTemperature = it }))
    }

    fun addTapper(light: LightRgbw) {
        tapLightData.add(LightData(light::setColorBrightness, { light.color = it }))
    }

    fun addTapper(light: LightRgbww) {
        tapLightData.add(LightData(light::setColorBrightness, { light.color = it }))
    }

    fun addJump(light: LightCwWw) {
        jumpLightData.add(LightData(light::setColorTemperatureBrightness, { light.colorTemperature = it }))
    }

    fun addJump(light: LightRgb) {
        jumpLightData.add(LightData(light::setColorBrightness, { light.color = it }))
    }

    fun addJump(light: LightRgbw) {
        jumpLightData.add(LightData(light::setWhiteBrightness, {}))
    }

    fun addJump(light: LightRgbww) {
        jumpLightData.add(LightData(light::setColorTemperatureBrightness, { light.color = it }))
    }

    fun addHands(light: LightRgbww) {
        handsLightData.add(LightData(light::setColorBrightness, { light.color = it }))
    }

    fun addQuad(light: LightCwWw) {
        quadLightData.add(LightData(light::setColorTemperatureBrightness, { light.colorTemperature = it }))
    }

    fun addQuad(light: LightRgbww) {
        quadLightData.add(LightData(light::setColorTemperatureBrightness, { light.colorTemperature = it }))
    }

    private var tempo: Int = 0
        set(value) {
            field = value
            dimRate = 0.0006F * value.toDouble().pow(1.3).toFloat() + 1
        }

    private var dimRate: Float = 1.015F
    private var startTime: Long = System.currentTimeMillis()
    private val palette: ColorExtractor.Palette = ColorExtractor.extractColors(
        when {
            song.background != null -> {
                song.background!!
            }
            song.banner != null -> {
                song.banner!!
            }
            song.cdTitle != null -> {
                song.cdTitle!!
            }
            else -> {
                throw RuntimeException("No colors!")
            }
        }
    )

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

        fun tap(color: Color = palette.primary()) {
            shouldDraw = true
            brightness = 1f
            setColor(color)
            held = false
        }

        fun hold(color: Color = palette.secondary()) {
            shouldDraw = true
            brightness = 1f
            held = true
            setColor(color)
        }

        fun draw() {
            setBrightness.invoke(brightness)
        }

        fun setColor(color: Color) {
            setColor.invoke(color)
        }
    }

    class Palette(private val primary: Int, private val secondary: Int, private val tertiary: Int) {

        fun primary() = getColor(primary)
        fun secondary() = getColor(secondary)
        fun tertiary() = getColor(tertiary)

        private fun getColor(int: Int) = Color(
            Color.HSBtoRGB(
                (int + rand.nextInt(MIN_COLOR_SEPARATION * 2) - MIN_COLOR_SEPARATION) / 360F, 1F, 1F
            )
        )

        companion object {
            private val rand = Random()
        }
    }

    companion object {
        private const val MIN_COLOR_SEPARATION = 20
        private const val FRAME_DELAY = (1000 / 20).toLong()
    }

    override fun tap(index: Int) {
//        val tapDiff = index - previousTapIndex
//        previousTapIndex = index
//
//        val stripDiff: Int = when (tapDiff) {
//            -3 -> 1
//            3 -> -1
//            else -> tapDiff
//        }
//
//        stripIndex += stripDiff
//        if (stripIndex < 0) {
//            stripIndex += tapLightData.size
//        } else if (stripIndex >= tapLightData.size) {
//            stripIndex -= tapLightData.size
//        }
//
//        tapLightData[stripIndex].tap()
        tapLightData[index].tap()
    }

    override fun jump(index: Int) {
        jumpLightData[index].tap(palette.secondary())
    }

    override fun hands(index: Int) {
        handsLightData[index % 3].tap(palette.tertiary())
    }

    override fun quad() {
        quadLightData[0].tap(palette.tertiary())
    }

    override fun hold(index: Int) {
        tapLightData[index].hold()
    }

    override fun release(index: Int) {
        tapLightData[index].held = false
    }

    override fun roll(index: Int) {
        tapLightData[index].hold()
//        TODO("Not yet implemented")
    }

    override fun mine(index: Int) {
//        TODO("Not yet implemented")
    }

    override fun tempo(tempo: Int) {
        this.tempo = tempo
    }

    override fun stop(duration: Int) {
        timeToStop = duration.toLong()
    }

}