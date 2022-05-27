package be.breina.stepmaniaparser.parsing

import be.breina.stepmaniaparser.model.Chart
import be.breina.stepmaniaparser.model.Event
import be.breina.stepmaniaparser.model.Song
import be.breina.stepmaniaparser.model.event.*
import java.util.regex.Pattern

object ChartParser {

    private const val BEATS_PER_MEASURE = 4

    private val lineBreak = Pattern.compile("\\r?\\n")
    private val measureBreak = Pattern.compile("\\r?\\n,")

    fun parseCharts(song: Song, chartType: String, description: String, difficulty: String, meter: String, grooveRadarString: String, noteData: String) {
        if (song.bpms == null) {
            throw RuntimeException("Can't parse chart before BPM is known")
        }

        val grooveRadar = grooveRadarString.split(',').map(ParsingHelper::secondsToMillis).toIntArray()

        val chart = Chart(chartType, description, difficulty, meter, grooveRadar)

        val measures = noteData.split(measureBreak)

        var currentTimeUs = 0L
        val bpmIterator = song.bpms!!.iterator()

        val nextBpm = bpmIterator.next()
        var bpmChangeTarget = nextBpm.first
        var newBpm = nextBpm.second

        var currentMBpm = 1

//        val stopIterator = song.stops!!.iterator()

//        val nextStop = stopIterator.next()
//        var stopChangeTarget = nextStop.first
//        var nextStopDuration = nextStop.second

        for (measureIndex in measures.indices) {
            val measure = measures[measureIndex]
            val measureLines = measure.split(lineBreak)
                .filter { line -> !Pattern.compile(" *//").asPredicate().test(line) }
                .filter { line -> !line.isEmpty() }
                .toList()

            val comment = measureLines[0]
            val rythm = measureLines.size // 4ths, 8ths, 12ths, ...

            var currentBeat = measureIndex * BEATS_PER_MEASURE * 1000

            for (line in measureLines) {
                // Update BPM
                var timeStep = timeStep(currentMBpm, rythm)
                val beatStep = BEATS_PER_MEASURE * 1000 / rythm

                if (currentBeat == bpmChangeTarget) {
                    currentMBpm = newBpm
                    timeStep = timeStep(currentMBpm, rythm)
                    chart.events.add(TempoChange(currentTimeUs, newBpm / 1000))

                    if (bpmIterator.hasNext()) {
                        val next = bpmIterator.next()
                        bpmChangeTarget = next.first
                        newBpm = next.second

                    } else {
                        bpmChangeTarget = Int.MAX_VALUE
                    }
                } else if (currentBeat + beatStep > bpmChangeTarget) {
                    val beatsBeforeBpmChange = (currentBeat + beatStep) - bpmChangeTarget
                    val beatsAfterBpmChange = beatStep - beatsBeforeBpmChange

                    val timeBeforeBpmChange = timeStep * beatsBeforeBpmChange / beatStep
                    currentMBpm = newBpm
                    chart.events.add(TempoChange(currentTimeUs + timeBeforeBpmChange, newBpm / 1000))

                    if (bpmIterator.hasNext()) {

                        val next = bpmIterator.next()
                        bpmChangeTarget = next.first
                        newBpm = next.second

                    } else {
                        bpmChangeTarget = Int.MAX_VALUE
                    }

                    timeStep = timeBeforeBpmChange + timeStep(currentMBpm, rythm) * beatsAfterBpmChange / beatStep
                }

//                if (currentBeat == stopChangeTarget) {
//                    chart.events.add(Stop(currentTimeUs, nextStopDuration))
//                    timeStep += nextStopDuration * 1000
//
//                    if (stopIterator.hasNext()) {
//                        val next = stopIterator.next()
//                        stopChangeTarget = next.first
//                        nextStopDuration = next.second
//                    } else {
//                        stopChangeTarget = Int.MAX_VALUE
//                    }
//                } else if (currentBeat + beatStep > stopChangeTarget) {
//                    val beatsBeforeStop = (currentBeat + beatStep) - bpmChangeTarget
//
//                    val timeUntilStop = timeStep * beatsBeforeStop / beatStep
//                    chart.events.add(Stop(currentTimeUs + timeUntilStop, nextStopDuration))
//                    timeStep += nextStopDuration * 1000
//
//                    if (stopIterator.hasNext()) {
//                        val next = stopIterator.next()
//                        stopChangeTarget = next.first
//                        nextStopDuration = next.second
//                    } else {
//                        stopChangeTarget = Int.MAX_VALUE
//                    }
//                }

                // Parse line
                parseLine(currentTimeUs, chart, line)

                // Advance time
                currentTimeUs += timeStep
                currentBeat += beatStep
            }
        }

        song.charts[chart.difficulty] = chart
    }

    private fun timeStep(currentMBpm: Int, rythm: Int) = (BEATS_PER_MEASURE * 1_000_000_000L * 60L / currentMBpm.toFloat() / rythm.toFloat()).toInt()

    private fun parseLine(currentTimeMs: Long, chart: Chart, line: String) {
        var index = -1
        var noteMask = 0

        for (c in line) {
            index++
            noteMask = noteMask shl 1

            if (c == '1') {
                noteMask = noteMask or 1
            } else {
                val event: Event = when (c) {
                    '0' -> continue
//                '1' -> {
//                    noteCount = noteCount or 1
////                    Tap(currentTimeMs, index)
//                }
                    '2' -> {
//                    noteCount = noteCount or 1
                        Hold(currentTimeMs, index)
                    }
                    '3' -> Release(currentTimeMs, index)
                    '4' -> {
//                    noteCount = noteCount or 1
                        Roll(currentTimeMs, index)
                    }
                    'M' -> Mine(currentTimeMs, index)
                    else -> throw RuntimeException("Unsupported note type: $c")
                }

                chart.events.add(event)
            }
        }

        val event = when (noteMask) {
            1 -> Tap(currentTimeMs, 3)
            2 -> Tap(currentTimeMs, 2)
            3 -> Jump(currentTimeMs, 5)
            4 -> Tap(currentTimeMs, 1)
            5 -> Jump(currentTimeMs, 4)
            6 -> Jump(currentTimeMs, 3)
            7 -> Hands(currentTimeMs, 3)
            8 -> Tap(currentTimeMs, 0)
            9 -> Jump(currentTimeMs, 2)
            10 -> Jump(currentTimeMs, 1)
            11 -> Hands(currentTimeMs, 2)
            12 -> Jump(currentTimeMs, 0)
            13 -> Hands(currentTimeMs, 1)
            14 -> Hands(currentTimeMs, 0)
            15 -> Quad(currentTimeMs)
            else -> return
        }

        chart.events.add(event)
    }
}