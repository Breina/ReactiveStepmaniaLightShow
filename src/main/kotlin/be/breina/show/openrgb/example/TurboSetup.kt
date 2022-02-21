package be.breina.show.openrgb.example

import be.breina.parser.mixer.Mixer
import be.breina.show.openrgb.OpenRgbException
import be.breina.show.openrgb.Setup
import be.breina.show.openrgb.animation.*
import be.breina.show.openrgb.devices.*
import be.breina.show.openrgb.linking.OpenRgbDeviceLinker
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import io.gitlab.mguimard.openrgb.entity.OpenRGBDevice
import java.time.Duration
import java.util.*
import java.util.function.IntConsumer

class TurboSetup : Setup {
    private var ramRight0: Strip? = null
    private var ramRight1: Strip? = null
    private var ramRight2: Strip? = null
    private var ramRight3: Strip? = null
    private var ramLeft0: Strip? = null
    private var ramLeft1: Strip? = null
    private var ramLeft2: Strip? = null
    private var ramLeft3: Strip? = null
    private var ioBlock: Strip? = null
    private var gpuLower: SingleLed? = null
    private var gpuUpper: SingleLed? = null
    private var motherboardSide: Strip? = null
    private var cpu: Strip? = null
    private var caseFront: Strip? = null
    private var chipset: Strip? = null
    private var mouseMat: Strip? = null
    private var fanSideTop: CorsairLLFan? = null
    private var fanSideMiddle: CorsairLLFan? = null
    private var fanSideBottom: CorsairLLFan? = null
    private var fanBottomBack: CorsairLLFan? = null
    private var fanBottomMiddle: CorsairLLFan? = null
    private var fanBottomFront: CorsairLLFan? = null
    private var fanTopBack: CorsairLLFan? = null
    private var fanTopMiddle: CorsairLLFan? = null
    private var fanTopFront: CorsairLLFan? = null
    private var reservoir: Strip? = null

    @Throws(OpenRgbException::class)
    override fun setupDevices(linker: OpenRgbDeviceLinker) {
        ramRight0 = linker.linkDevice("I2C: AMD SMBus at 0x0B00, address 0x71", ::Strip)
        ramRight1 = linker.linkDevice("I2C: AMD SMBus at 0x0B00, address 0x73", ::Strip)
        ramRight2 = linker.linkDevice("I2C: AMD SMBus at 0x0B00, address 0x75", ::Strip)
        ramRight3 = linker.linkDevice("I2C: AMD SMBus at 0x0B00, address 0x76", ::Strip)
        ramLeft0 = linker.linkDevice("I2C: AMD SMBus at 0x0B00, address 0x78", ::Strip)
        ramLeft1 = linker.linkDevice("I2C: AMD SMBus at 0x0B00, address 0x79", ::Strip)
        ramLeft2 = linker.linkDevice("I2C: AMD SMBus at 0x0B00, address 0x7A", ::Strip)
        ramLeft3 = linker.linkDevice("I2C: AMD SMBus at 0x0B00, address 0x7B", ::Strip)
        linker.linkDevice(
            "HID: \\\\?\\hid#vid_0b05&pid_18f3&mi_02#b&395d80fb&0&0000#{4d1e55b2-f16f-11cf-88cb-001111000030}"
        ) { index: Int, _: OpenRGBDevice? ->
            MultiDevice(index, listOf(
                Strip(index, 2).also {
                    ioBlock = it
                },
                SingleLed(index).also {
                    gpuLower = it
                },
                SingleLed(index).also {
                    gpuUpper = it
                },
                Strip(index, 5).also {
                    motherboardSide = it
                },
                Strip(index, 5).also {
                    cpu = it
                },
                Strip(index, 28).also {
                    caseFront = it
                },
                Strip(index, 9).also {
                    chipset = it
                }
            ))
        }

        mouseMat = linker.linkDevice("HID: \\\\?\\hid#vid_1b1c&pid_1b3b&mi_00#b&336f2ae9&0&0000#{4d1e55b2-f16f-11cf-88cb-001111000030}", ::Strip)
        linker.linkDevice(
            "HID: \\\\?\\hid#vid_1b1c&pid_0c0b#a&154e50c2&0&0000#{4d1e55b2-f16f-11cf-88cb-001111000030}"
        ) { index: Int, _: OpenRGBDevice? ->
            MultiDevice(index, listOf<RgbDevice>(
                CorsairLLFan(index).also {
                    fanSideTop = it
                },
                CorsairLLFan(index).also {
                    fanSideMiddle = it
                },
                CorsairLLFan(index).also {
                    fanSideBottom = it
                },
                CorsairLLFan(index).also {
                    fanBottomBack = it
                },
                CorsairLLFan(index).also {
                    fanBottomMiddle = it
                },
                CorsairLLFan(index).also {
                    fanBottomFront = it
                },
                CorsairLLFan(index).also {
                    fanTopBack = it
                },
                CorsairLLFan(index).also {
                    fanTopMiddle = it
                },
                CorsairLLFan(index).also {
                    fanTopFront = it
                },
                Strip(index, 27).also {
                    reservoir = it
                }
            ))
        }
    }

    override fun setupAnimations(animator: Animator): Mixer {
        return object : Mixer {
            private val holds = WeakHashMap<Int, List<HoldableColorFade>>()
            private var previousTapIndex = 0
            private var tappyIndex = 15

            private var baseDuration = Duration.ofMillis(100)

            override fun tap(index: Int) {
                updateTappy(index)

                animateCpuTap(index, baseDuration, animator.getPrimaryColor(), animator)

                val tapDuration = baseDuration.multipliedBy(2)
                animateRamTap(index, tapDuration, animator.getPrimaryColor(), animator)
                animateStripTap(motherboardSide!!, tappyIndex, tapDuration, animator)
                animateStripTap(reservoir!!, tappyIndex, tapDuration, animator)
                animateStripTap(caseFront!!, tappyIndex, tapDuration, animator)

                previousTapIndex = index
            }

            override fun jump(index: Int) {
                animateJumpAsTaps(index) { tapIndex: Int -> animateRamTap(tapIndex, baseDuration.multipliedBy(3), animator.getSecondaryColor(), animator) }
                animateJumpAsTaps(index) { tapIndex: Int -> animateCpuTap(tapIndex, baseDuration, animator.getSecondaryColor(), animator) }

                val jumpDuration = baseDuration.multipliedBy(5)
                animateFanJumps(index, jumpDuration, animator)
            }

            override fun hands(index: Int) {
                animateHandsAsTaps(index) { tapIndex: Int -> animateRamTap(tapIndex, baseDuration.multipliedBy(4), animator.getSecondaryColor(), animator) }
                animateHandsAsTaps(index) { tapIndex: Int -> animateCpuTap(tapIndex, baseDuration, animator.getSecondaryColor(), animator) }
                animateGpuHands(index, baseDuration.multipliedBy(5), animator)
            }

            override fun quad() {
                val quadDuration = baseDuration.multipliedBy(5)
                animateQuadAsTaps { tapIndex: Int -> animateRamTap(tapIndex, baseDuration.multipliedBy(4), animator.getPrimaryColor(), animator) }
                animateQuadAsTaps { tapIndex: Int -> animateCpuTap(tapIndex, baseDuration, animator.getSecondaryColor(), animator) }
                animator.addAnimations(ColorFade(caseFront!!, animator.getSecondaryColor(), quadDuration))
                animator.addAnimations(ColorFade(reservoir!!, animator.getSecondaryColor(), quadDuration))
            }

            override fun hold(index: Int) {
                val holdDuration = baseDuration.multipliedBy(2)

                val (first, second) = getRamsByTapIndex(index)
                val firstRamAnimation = HoldableColorFade(first, animator.getTertiaryColor(), holdDuration)
                val secondRamAnimation = HoldableColorFade(second, animator.getTertiaryColor(), holdDuration)
                animator.addAnimations(firstRamAnimation, secondRamAnimation)
                holds[index] = listOf(firstRamAnimation, secondRamAnimation)
            }

            override fun release(index: Int) {
                val holdableColorFades = holds[index]!!
                holdableColorFades.forEach(HoldableColorFade::release)
            }

            override fun tempo(tempo: Int) {
                baseDuration = Duration.ofMillis((18000 / tempo).toLong())
            }

            override fun roll(index: Int) {}
            override fun mine(index: Int) {}
            override fun stop(duration: Int) {}

            private fun updateTappy(newTapIndex: Int) {
                when (val diff = newTapIndex - previousTapIndex) {
                    -3 -> tappyIndex += 1
                    +3 -> tappyIndex -= 1
                    else -> tappyIndex += diff
                }
            }
        }
    }

    private fun animateFanJumps(index: Int, duration: Duration, animator: Animator) {
        val bottomOrSideFan: CorsairLLFan?
        val topFan: CorsairLLFan?
        when (index) {
            0 -> {
                bottomOrSideFan = fanBottomBack
                topFan = fanTopBack
            }
            1 -> {
                bottomOrSideFan = fanBottomMiddle
                topFan = fanTopBack
            }
            2 -> {
                bottomOrSideFan = fanBottomFront
                topFan = fanTopMiddle
            }
            3 -> {
                bottomOrSideFan = fanSideBottom
                topFan = fanTopMiddle
            }
            4 -> {
                bottomOrSideFan = fanSideMiddle
                topFan = fanTopFront
            }
            5 -> {
                bottomOrSideFan = fanSideTop
                topFan = fanTopFront
            }
            else -> throw IllegalStateException("Unexpected value: $index")
        }
        animator.addAnimations(FanColorFade(bottomOrSideFan!!, duration, animator.getSecondaryColor()))
        animator.addAnimations(FanColorFade(topFan!!, duration, animator.getSecondaryColor()))
    }

    private fun animateGpuHands(index: Int, duration: Duration, animator: Animator) {
        val gpu = if (index % 2 == 0) gpuLower else gpuUpper
        animator.addAnimations(ColorFade(gpu!!, animator.getSecondaryColor(), duration))
    }

    private fun animateCpuTap(index: Int, duration: Duration, color: OpenRGBColor, animator: Animator) {
        val cpuIndex: Int = when (index) {
            0, 1 -> index
            2, 3 -> index + 1
            else -> throw IllegalStateException("Unexpected value: $index")
        }
        animator.addAnimations(
            ColorFade(cpu!!, cpuIndex, color, duration)
        )
    }

    private fun animateRamTap(index: Int, duration: Duration, color: OpenRGBColor, animator: Animator) {
        val (first, second) = getRamsByTapIndex(index)
        animator.addAnimations(
            InOutWave(first, color, duration),
            InOutWave(second, color, duration)
        )
    }

    private fun getRamsByTapIndex(index: Int): Pair<Strip, Strip> {
        val rams: Pair<Strip, Strip> = when (index) {
            0 -> Pair(ramLeft0!!, ramRight0!!)
            1 -> Pair(ramLeft1!!, ramRight1!!)
            2 -> Pair(ramLeft2!!, ramRight2!!)
            3 -> Pair(ramLeft3!!, ramRight3!!)
            else -> throw IllegalStateException("Unexpected value: $index")
        }
        return rams
    }

    companion object {
        private fun animateJumpAsTaps(jumpIndex: Int, tapAnimate: IntConsumer) {
            when (jumpIndex) {
                0 -> {
                    tapAnimate.accept(0)
                    tapAnimate.accept(1)
                }
                1 -> {
                    tapAnimate.accept(0)
                    tapAnimate.accept(2)
                }
                2 -> {
                    tapAnimate.accept(0)
                    tapAnimate.accept(3)
                }
                3 -> {
                    tapAnimate.accept(1)
                    tapAnimate.accept(2)
                }
                4 -> {
                    tapAnimate.accept(1)
                    tapAnimate.accept(3)
                }
                5 -> {
                    tapAnimate.accept(2)
                    tapAnimate.accept(3)
                }
                else -> throw IllegalStateException("Unexpected value: $jumpIndex")
            }
        }

        private fun animateHandsAsTaps(handIndex: Int, tapAnimate: IntConsumer) {
            when (handIndex) {
                0 -> {
                    tapAnimate.accept(0)
                    tapAnimate.accept(1)
                    tapAnimate.accept(2)
                }
                1 -> {
                    tapAnimate.accept(0)
                    tapAnimate.accept(1)
                    tapAnimate.accept(3)
                }
                2 -> {
                    tapAnimate.accept(0)
                    tapAnimate.accept(2)
                    tapAnimate.accept(3)
                }
                3 -> {
                    tapAnimate.accept(1)
                    tapAnimate.accept(2)
                    tapAnimate.accept(3)
                }
                else -> throw IllegalStateException("Unexpected value: $handIndex")
            }
        }

        private fun animateQuadAsTaps(tapAnimate: IntConsumer) {
            tapAnimate.accept(0)
            tapAnimate.accept(1)
            tapAnimate.accept(2)
            tapAnimate.accept(3)
        }

        private fun animateStripTap(strip: Strippable, tappyIndex: Int, duration: Duration, animator: Animator) {
            var stripIndex = tappyIndex % strip.length()
            if (stripIndex < 0) {
                stripIndex += strip.length()
            }
            animator.addAnimations(ColorFade(strip as RgbDevice, stripIndex, animator.getPrimaryColor(), duration))
        }
    }
}