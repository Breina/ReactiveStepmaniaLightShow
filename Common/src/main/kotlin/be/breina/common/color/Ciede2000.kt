package be.breina.common.color

import kotlin.math.*

/**
 * @author https://github.com/wuchubuzai/OpenIMAJ/blob/master/image/image-processing/src/main/java/org/openimaj/image/analysis/colour/CIEDE2000.java
 */
object Ciede2000 {
    /**
     * Calculate the colour difference value between two colours in lab space.
     * @param l1 first colour's L component
     * @param a1 first colour's a component
     * @param b1 first colour's b component
     * @param l2 second colour's L component
     * @param a2 second colour's a component
     * @param b2 second colour's b component
     * @return the CIE 2000 colour difference
     */
    fun calculateDeltaE(l1: Double, a1: Double, b1: Double, l2: Double, a2: Double, b2: Double): Double {
        val lmean = (l1 + l2) / 2.0 //ok
        val C1 = sqrt(a1 * a1 + b1 * b1) //ok
        val C2 = sqrt(a2 * a2 + b2 * b2) //ok
        val Cmean = (C1 + C2) / 2.0 //ok
        val G =
            (1 - sqrt(Cmean.pow(7.0) / (Cmean.pow(7.0) + 25.0.pow(7.0)))) / 2 //ok
        val a1prime = a1 * (1 + G) //ok
        val a2prime = a2 * (1 + G) //ok
        val C1prime = sqrt(a1prime * a1prime + b1 * b1) //ok
        val C2prime = sqrt(a2prime * a2prime + b2 * b2) //ok
        val Cmeanprime = (C1prime + C2prime) / 2 //ok
        val h1prime = atan2(b1, a1prime) + 2 * Math.PI * if (atan2(b1, a1prime) < 0) 1 else 0
        val h2prime = atan2(b2, a2prime) + 2 * Math.PI * if (atan2(b2, a2prime) < 0) 1 else 0
        val Hmeanprime =
            if (abs(h1prime - h2prime) > Math.PI) (h1prime + h2prime + 2 * Math.PI) / 2 else (h1prime + h2prime) / 2 //ok
        val T =
            1.0 - 0.17 * cos(Hmeanprime - Math.PI / 6.0) + 0.24 * cos(2 * Hmeanprime) + 0.32 * cos(
                3 * Hmeanprime + Math.PI / 30
            ) - 0.2 * cos(4 * Hmeanprime - 21 * Math.PI / 60) //ok
        val deltahprime =
            if (abs(h1prime - h2prime) <= Math.PI) h2prime - h1prime else if (h2prime <= h1prime) h2prime - h1prime + 2 * Math.PI else h2prime - h1prime - 2 * Math.PI //ok
        val deltaLprime = l2 - l1 //ok
        val deltaCprime = C2prime - C1prime //ok
        val deltaHprime = 2.0 * sqrt(C1prime * C2prime) * sin(deltahprime / 2.0) //ok
        val SL = 1.0 + 0.015 * (lmean - 50) * (lmean - 50) / sqrt(20 + (lmean - 50) * (lmean - 50)) //ok
        val SC = 1.0 + 0.045 * Cmeanprime //ok
        val SH = 1.0 + 0.015 * Cmeanprime * T //ok
        val deltaTheta =
            30 * Math.PI / 180 * exp(-((180 / Math.PI * Hmeanprime - 275) / 25) * ((180 / Math.PI * Hmeanprime - 275) / 25))
        val RC =
            2 * sqrt(Cmeanprime.pow(7.0) / (Cmeanprime.pow(7.0) + 25.0.pow(7.0)))
        val RT = -RC * sin(2 * deltaTheta)
        val KL = 1.0
        val KC = 1.0
        val KH = 1.0
        return sqrt(
            deltaLprime / (KL * SL) * (deltaLprime / (KL * SL)) +
                    deltaCprime / (KC * SC) * (deltaCprime / (KC * SC)) +
                    deltaHprime / (KH * SH) * (deltaHprime / (KH * SH)) +
                    RT * (deltaCprime / (KC * SC)) * (deltaHprime / (KH * SH))
        )
    }
}