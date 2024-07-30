package deus.away.engine.core.media.audio

import deus.away.engine.core.systems.signal.Signal
import javax.sound.sampled.Clip

abstract class AudioPlayer {

    val onStop = Signal<Unit>()
    val onFinish = Signal<Unit>()
    val onStart = Signal<Unit>()
    val onPause = Signal<Unit>()
    val onLoopFinish = Signal<Unit>()
    var loop: Boolean = true
    var finished: Boolean = false
    var loopTimes: Int = -1

    open fun restart() {

    }

    open fun play() {
        onStart.emit(Unit)
    }

    open fun stop() {
        onStop.emit(Unit)
        finished = true
    }

    open fun pause() {
        onPause.emit(Unit)
    }

    open fun loop(boolean: Boolean, times: Int) {
    }

    open fun change(newClip: Clip) {

    }

}