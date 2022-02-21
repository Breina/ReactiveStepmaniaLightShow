package be.breina.parser.model

import java.awt.image.BufferedImage
import java.io.File
import java.util.*

/**
 * https://github.com/stepmania/stepmania/wiki/sm
 */
class Song(
    /**
     * Sets the primary title of the song.
     */
    var title: String? = null,
    /**
     * Sets the subtitle of the song.
     */
    var subtitle: String? = null,
    /**
     * Sets the artist of the song.
     */
    var artist: String? = null,
    /**
     * Sets the transliterated primary title of the song, which is used when ShowNativeLanguage=0.
     */
    var titleTranslit: String? = null,
    /**
     * Sets the transliterated subtitle of the song, which is used when ShowNativeLanguage=0.
     */
    var subtitleTranslit: String? = null,
    /**
     * Sets the transliterated artist of the song, which is used when ShowNativeLanguage=0.
     */
    var artistTranslit: String? = null,
    /**
     * Sets the genre of the song.
     */
    var genre: String? = null,
    /**
     * Defines the simfile's origin (author or pack/mix).
     */
    var credit: String? = null,
    /**
     * Sets the path to the banner image for the song. Banner images are typically rectangular, with common sizes being 256x80 (DDR), 418x164 (ITG), and 512x160 (DDR doubleres).
     */
    var banner: BufferedImage? = null,
    /**
     * Sets the path to the background image for the song. Background images are typically 640x480 or greater in resolution.
     */
    var background: BufferedImage? = null,
    /**
     * Sets the path to the lyrics file (.lrc) to use.
     */
    var lyricsPath: File? = null,
    /**
     * Sets the path to the CD Title, a small image meant to show the origin of the song. The recommended size is around 64x48.
     */
    var cdTitle: BufferedImage? = null,
    /**
     * Sets the path to the music file for this song.
     */
    var music: File? = null,
    /**
     * Sets the offset between the beginning of the song and the start of the note data in seconds.
     */
    var offset: Int? = null,
    /**
     * Sets the BPMs for this song. BPMS are defined in the format Beat=BPM, with each value separated by a comma.
     */
    var bpms: LinkedList<Pair<Int, Int>>? = null,
    /**
     * Sets the stops for this song. Stops are defined in the format Beat=Seconds, with each value separated by a comma.
     */
    var stops: LinkedList<Pair<Int, Int>>? = null,
    /**
     * Sets the start time of the song sample used on ScreenSelectMusic.
     */
    var sampleStart: Int? = null,
    /**
     * Sets the length of the song sample used on ScreenSelectMusic.
     */
    var sampleLength: Int? = null,
    /**
     * This can be used to override the BPM shown on ScreenSelectMusic. This tag supports three types of values:<br></br>
     * - A number by itself (e.g. #DISPLAYBPM:180;) will show a static BPM.<br></br>
     * - Two numbers in a range (e.g. #DISPLAYBPM:90:270;) will show a BPM that changes between two values.<br></br>
     * - An asterisk (#DISPLAYBPM:*;) will show a BPM that randomly changes.
     */
    var displayBpm: String? = null,
    /**
     * Determines if the song is selectable from the MusicWheel under normal conditions. Valid values are YES and NO.
     */
    var selectable: Boolean? = null,
    var charts: MutableMap<String, Chart> = HashMap()
) {
    override fun toString(): String =
        "Song(title=$title, subtitle=$subtitle, artist=$artist, titleTranslit=$titleTranslit, subtitleTranslit=$subtitleTranslit, artistTranslit=$artistTranslit, genre=$genre, credit=$credit, banner=$banner, background=$background, lyricsPath=$lyricsPath, cdTitle=$cdTitle, music=$music, offset=$offset, bpms=$bpms, stops=$stops, sampleStart=$sampleStart, sampleLength=$sampleLength, displayBpm=$displayBpm, selectable=$selectable, charts=$charts)"
}