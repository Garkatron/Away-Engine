package deus.away.engine.core.systems.controller

import deus.away.engine.core.systems.signal.Signal
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class KeyboardListener {

    val keyPressed = Signal<Char>()
    val keyUp = Signal<Int>()
    val keyDown = Signal<Int>()

}