package deus.away.engine.core.media.audio

import javax.sound.sampled.Clip
import javax.sound.sampled.LineEvent

class WAVPlayer(private var clip: Clip) : AudioPlayer() {

    init {
        clip.addLineListener { event ->
            when (event.type) {
                LineEvent.Type.STOP -> {
                    println("Playback finished.")
                    finished = true
                }

                LineEvent.Type.START -> {
                    println("Playback started.")
                    finished = false
                }
            }
        }
    }

    override fun restart() {
        clip.framePosition = 0
    }

    override fun play() {
        if (clip.isRunning) {
            if (!loop)
                clip.stop()
        }
        if (!loop) {
            clip.framePosition = 0  // Reinicia el clip antes de reproducir
            clip.start()
        } else {
            clip.loop(Clip.LOOP_CONTINUOUSLY)
        }
        println("Playing the clip.")
    }

    override fun stop() {
        clip.stop()
        println("Clip stopped.")
    }

    override fun change(newClip: Clip) {
        if (clip.isRunning) {
            stop()
        }
        clip = newClip
    }

    fun enableLoop(enable: Boolean) {
        loop = enable
    }

}
