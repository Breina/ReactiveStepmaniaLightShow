package be.breina.player.swing

import be.breina.player.Mixer
import be.breina.player.model.Palette
import be.breina.player.model.SongTheming
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JPanel
import kotlin.math.pow

class JLightShowPanel(songTheming: SongTheming) : JPanel(), Mixer {

    private val stripData: Array<CircleData> = Array(20) { CircleData() }
    private var stripIndex: Int = 0
    private var previousTapIndex: Int = 0

    private val tapCircleData: Array<CircleData> = Array(4) { CircleData() }
    private val jumpCircleData: Array<CircleData> = Array(6) { CircleData() }
    private val handsCircleData: Array<CircleData> = Array(6) { CircleData() }
    private val quadCircleData: CircleData = CircleData()
    private var tempo: Int = 0
        set(value) {
            field = value
            shrinkRate = 0.00002F * value.toDouble().pow(1.3).toFloat() + 1
        }

    private var shrinkRate: Float = 1.015F
    private var startTime: Long = System.currentTimeMillis()
    private val palette: Palette = songTheming.palette

    init {

        Thread {
            while (true) {
                val circleShrink: (CircleData) -> Unit = { circleData ->
                    if (!circleData.held) {
                        circleData.size /= shrinkRate
                    }

                    if (circleData.size < 0.1f) {
                        circleData.shouldDraw = false
                    }
                }

                stripData.forEach(circleShrink)
                tapCircleData.forEach(circleShrink)
                jumpCircleData.forEach(circleShrink)
                handsCircleData.forEach(circleShrink)
                circleShrink.invoke(quadCircleData)
                repaint()

                Thread.sleep(FRAME_DELAY)
            }
        }.start()
    }

    override fun paint(gOld: Graphics) {
        super.paint(gOld)
        val g = gOld as Graphics2D
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.background = Color.BLACK
        g.clearRect(0, 0, width, height)

        g.color = Color.WHITE
        g.drawString("$tempo BPM", 30, 30)

        val relativeTime = "%.3f".format((System.currentTimeMillis() - startTime) / 1000F)
        g.drawString("$relativeTime s", 30, 50)

        val yOffset = height / 5

        val xOffset20 = width / 21
        val maxCircleSize20 = xOffset20 / 2

        for (index in stripData.indices) {
            val circle = stripData[index]

            if (circle.shouldDraw) {
                g.color = circle.color
                val circleSize = (maxCircleSize20 * circle.size).toInt()
                g.fillOval(xOffset20 * (index + 1) - circleSize / 2, yOffset / 2 - circleSize / 2, circleSize, circleSize)
            }
        }

        val xOffset4 = width / 5
        val maxCircleSize4 = xOffset4 / 2

        for (index in tapCircleData.indices) {
            val circle = tapCircleData[index]

            if (circle.shouldDraw) {
                g.color = circle.color
                val circleSize = (maxCircleSize4 * circle.size).toInt()
                g.fillOval(xOffset4 * (index + 1) - circleSize / 2, yOffset - circleSize / 2, circleSize, circleSize)
            }
        }

        val xOffset6 = width / 7
        val maxCircleSize6 = xOffset6 / 2

        for (index in jumpCircleData.indices) {
            val circle = jumpCircleData[index]

            if (circle.shouldDraw) {
                g.color = circle.color
                val circleSize = (maxCircleSize6 * circle.size * 2).toInt()
                g.fillOval(xOffset6 * (index + 1) - circleSize / 2, yOffset * 2 - circleSize / 2, circleSize, circleSize)
            }
        }

        for (index in handsCircleData.indices) {
            val circle = handsCircleData[index]

            if (circle.shouldDraw) {
                g.color = circle.color
                val circleSize = (maxCircleSize4 * circle.size * 3).toInt()
                g.fillOval(xOffset4 * (index + 1) - circleSize / 2, yOffset * 3 - circleSize / 2, circleSize, circleSize)
            }
        }

        if (quadCircleData.shouldDraw) {
            g.color = quadCircleData.color
            val circleSize = (width / 2 * quadCircleData.size).toInt()

            g.fillOval(width / 2 - circleSize / 2, yOffset * 4 - circleSize / 2, circleSize, circleSize)
        }
    }

    inner class CircleData {
        var held: Boolean = false
        var shouldDraw: Boolean = false
        var size: Float = 0f
        var color: Color = Color.BLACK

        fun tap(color: Color = palette.primary) {
            shouldDraw = true
            size = 1f
            this@CircleData.color = color
            held = false
        }

        fun hold(color: Color = palette.secondary) {
            shouldDraw = true
            size = 1f
            held = true
            this@CircleData.color = color
        }
    }

    companion object {
        private const val FRAME_DELAY = (1000f / 250f).toLong()
    }

    override fun single(index: Int) {
        tapCircleData[index].tap()

        val tapDiff = index - previousTapIndex
        previousTapIndex = index

        val stripDiff: Int = when (tapDiff) {
            -3 -> 1
            3 -> -1
            else -> tapDiff
        }

        stripIndex += stripDiff
        if (stripIndex < 0) {
            stripIndex += 20
        } else if (stripIndex >= 20) {
            stripIndex -= 20
        }

        stripData[stripIndex].tap()
    }

    override fun double(index: Int) {
        jumpCircleData[index].tap(palette.tertiary)
    }

    override fun triple(index: Int) {
        handsCircleData[index].tap(palette.tertiary)
    }

    override fun quad() {
        quadCircleData.tap(palette.tertiary)
    }

    override fun longStart(index: Int) {
        tapCircleData[index].hold()
    }

    override fun burstStart(index: Int) {
        tapCircleData[index].hold()
//        TODO("Not yet implemented")
    }

    override fun longOrBurstEnd(index: Int) {
        tapCircleData[index].held = false
    }

    override fun omit(index: Int) {
//        TODO("Not yet implemented")
    }

    override fun tempo(tempo: Int) {
        this.tempo = tempo
    }

    override fun pause(duration: Int) {
        TODO("Not yet implemented")
    }
}