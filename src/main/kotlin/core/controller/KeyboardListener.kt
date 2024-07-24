package core.controller

import core.signal.Signal
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class KeyboardListener : KeyListener {

    val keyPressed = Signal<Char>()
    val keyUp = Signal<Int>()
    val keyDown = Signal<Int>()

    override fun keyTyped(e: KeyEvent?) {
        e?.let {
            //println("Key Typed: ${e.keyChar}")
            keyPressed.emit(e.keyChar)
        }
    }

    override fun keyPressed(e: KeyEvent?) {

        e?.let {
            //println("Key Pressed: ${e.keyCode}")
            keyDown.emit(e.keyCode)
        }
    }

    override fun keyReleased(e: KeyEvent?) {
        e?.let {
            //println("Key Released: ${e.keyCode}")
            keyUp.emit(e.keyCode)
        }
    }

}