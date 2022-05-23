package be.breina.parser

import be.breina.parser.io.sm.SMParser
import be.breina.parser.mixer.PolyMixer
import be.breina.parser.mixer.SwingMixer
import be.breina.parser.model.Chart
import be.breina.show.dmx.DmxMixer
import be.breina.show.openrgb.example.TurboSetup
import be.breina.show.openrgb.linking.OpenRgbBootstrapper
import java.nio.file.Path

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val song = SMParser.parse(
            Path.of(
//                "F:\\Games\\Etterna\\Songs\\Xingyue's ETTERNA V\\Animals\\Animals.sm"
//                "F:\\Games\\Etterna\\Songs\\Agressive Dump Pack\\Earthquake\\HD Dubstep - Earthquake (Fytch Remix) [FEATURED] - [MP3JUICES.COM].sm"
//                "F:\\Games\\Etterna\\Songs\\Agressive Dump Pack\\All the things she said  DNB MIX\\all the things she said dnb.sm"
//                "F:\\Games\\Etterna\\Songs\\Agressive Dump Pack\\Bass cannon\\Bass Cannon.sm"
//                "F:\\Games\\Etterna\\Songs\\Nuclear Blast JS Awesome Bomb Filez 3\\11eleven (IcyWorld)\\DJ Sharpnel - 11eleven.sm"
//                "F:\\Games\\Etterna\\Songs\\Astronomic Metal Collection 2\\(Patashu) Hatebreeder\\Hatebreeder.sm"
//                "F:\\Games\\Etterna\\Songs\\Astronomic Metal Collection\\Fatal tragedy (KangXIX)\\Fatal Tragedy.sm"
//                "F:\\Games\\Etterna\\Songs\\FFR Community Pack Winter 2011\\Blackened (Baq12)\\Blackened.sm"
//                "F:\\Games\\Etterna\\Songs\\Hayden\\A\\A.sm"
//                "F:\\Games\\Etterna\\Songs\\Hard Songs Megapack Volume 2\\Please Listen With X-Rated Videos MIX\\X-rated.sm"
//            "F:\\Games\\Etterna\\Songs\\hard shit\\Death Piano\\Death.sm" // Broken
//                "F:\\Games\\Etterna\\Songs\\Hard Songs Megapack Volume 4\\How Fast Cen U Roll Niga What\\how_fast_cen_u_roll_niga_wut.sm"
//                "F:\\Games\\Etterna\\Songs\\Hard Songs Megapack Volume 12\\Flandre's Theme Piano Remix\\Flandre's Theme.sm"
//                "F:\\Games\\Etterna\\Songs\\Hard Songs Megapack Volume 5\\Pink Magic\\zen.sm"
//            "F:\\Games\\Etterna\\Songs\\Yolo Dumps 1\\AiAe (Wafles)\\04. AiAe.sm"
//                "F:\\Games\\Etterna\\Songs\\Guitar Hero Mega Pack\\Smoke on the Water\\Smoke on the Water.sm"
//                "F:\\Games\\Etterna\\Songs\\Nuclear Blast JS Awesome Bomb Filez 5\\Soldiers of The Stamina (IcyWorld)\\DragonForce - Soldiers Of The Wasteland.sm"
//                "F:\\Games\\Etterna\\Songs\\Nuclear Blast JS Awesome Bomb Filez 5\\Nothing Left (MetzgerSM)\\02 Nothing Left.sm"
//                "F:\\Games\\Etterna\\Songs\\Xoon 4 Blue Version\\(who_cares973) River Flows In You\\Yiruma_RiverFlowsInYou.sm"
//                "F:\\Games\\Etterna\\Songs\\midare megapack 5\\Dog Cooking Eggs\\dog.sm"
                "F:\\Games\\Etterna\\Songs\\Hard Songs Megapack Volume 11\\Stinger\\the flashbulb - stinger.sm"
//                "F:\\Games\\Etterna\\Songs\\Hard Songs Megapack Volume 9\\La Campanella\\La Campanella(who_cares973).sm"
//                "F:\\Games\\Etterna\\Songs\\Hard Songs Megapack Volume 1\\St. Scarhand\\scarhand.sm"
//                "F:\\Games\\Etterna\\Songs\\Xoon 4 Blue Version\\(Baq12) Eruption\\002 Van Halen - Eruption.sm"
//                "F:\\Games\\Etterna\\Songs\\Nuclear Blast JS Awesome Bomb Filez 5\\The Nature of Dying (IcyWorld)\\goreshit - semantic compositions on death and its meaning - 01 the nature of dying.sm"
//                "F:\\Games\\Etterna\\Songs\\Hard Songs Megapack Volume 1\\Fury of The Storm EDIT\\Fury.sm"
//                "F:\\Games\\Etterna\\Songs\\Hard Songs Megapack Volume 1\\Reality - Reach\\Reality.sm"
//                "F:\\Games\\Etterna\\Songs\\Hard Songs Megapack Volume 1\\Vertex Gamma\\vertexgamma.sm"
//                "F:\\Games\\Etterna\\Songs\\Definitive Keyboard Megapack\\Rose(Cartoon)\\Rose - cornandbeans.sm"
//                "F:\\Games\\Etterna\\Songs\\ODIPack3\\[Nick Skyline] - Fallen World\\oh my fucking god another dragonforce file what bullshit.sm"
//            "F:\\Games\\Etterna\\Songs\\Stepocalypse Set 1 - Tiers\\[2-SPD] Bokura no 16Bit Wars\\16warz.sm"
//            "F:\\Games\\Etterna\\Songs\\Hard Songs Megapack Volume 12\\Jehova's YaHVeH\\Jehova's YaHVeH.sm"
//                "F:\\Games\\Etterna\\Songs\\Stepocalypse Set 1 - Tiers\\[6-STM] Thru Our Scars\\01 Thru Our Scars.sm"
//                "F:\\Games\\Etterna\\Songs\\Stepocalypse Set 1 - Tiers\\[4-STM] Unorthodox Red\\DJ Nanasaki - Unorthodox Red.sm"
//                "F:\\Games\\Etterna\\Songs\\Stepocalypse Set 1 - Tiers\\[7-SPD] Realization\\Faylan - Realization.sm"
//                "F:\\Games\\Etterna\\Songs\\Stepocalypse Set 1 - Tiers\\[7-STM] IMAGE -MATERIAL-[Version 0]\\03 IMAGE -MATERIAL-(Version 0).sm"
//                "F:\\Games\\Etterna\\Songs\\Stepocalypse Set 1 - Tiers\\[8-XXX] Rave7\\Rave7.sm"
//                "F:\\Games\\Etterna\\Songs\\Stepocalypse Set 2 - PUSH IT\\26. Pine nut (ATTangvsDroptable)\\Pine nut.sm"
//            "F:\\Games\\Etterna\\Songs\\Nuclear Kimchi Chordjack Pack\\Set Fire to the Rain (aeyeong)\\01 - Adele - Set Fire To The Rain.sm"
//            "F:\\Games\\Etterna\\Songs\\German Dump Mini Pack 1\\Swirling Toilet bowl of Glorious Dumps Pack V1 !!\\Wizards in Winter (Anaru)\\Wizards in Winter.sm"
//            "F:\\Games\\Etterna\\Songs\\Yolo Dumps 1\\Air (Alioth)\\[Alioth] SHIKI - Air.sm"
//            "F:\\Games\\Etterna\\Songs\\CMDKDF Pack\\Cry Of The Brave (Zaghurim)\\Cry of the Brave.sm"
            )
        )

        val chart: Chart =
            song.charts["Challenge"] ?: song.charts["Hard"] ?: song.charts["Medium"] ?: song.charts["Easy"] ?: song.charts["Beginner"]!!

        // Java Swing
//        Player.play(song, chart, SwingMixer(song))
//        val swingMixer = SwingMixer(song)

        // DMX
//        val dmxMixer = DmxMixer(song)
//        Player.play(song, chart, dmxMixer.animator)

        // OpenRGB
        val bootstrapper = OpenRgbBootstrapper("localhost", 6742, song, TurboSetup())
        bootstrapper.getMainLoop().play()
        val openRgbMixer = bootstrapper.getMixer()
//        Player.play(song, chart, bootstrapper.getMixer())

//        Player.play(song, chart, PolyMixer(listOf(swingMixer, openRgbMixer)));
        Player.play(song, chart, openRgbMixer)
    }
}