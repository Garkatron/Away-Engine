package screen

import core.controller.KeyboardListener
import java.awt.Component
import javax.swing.JFrame

class Window(title: String = "Screen Window", width: Int = 400, height: Int = 300) {
    private val frame: JFrame = JFrame(title).apply {
        setSize(width, height)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null) // Centra la ventana en la pantalla
    }

    fun show() {
        frame.isVisible = true
    }

    fun setTitle(title: String) {
        frame.title = title
    }

    fun setSize(width: Int, height: Int) {
        frame.setSize(width, height)
    }

    fun setLocation(x: Int, y: Int) {
        frame.setLocation(x, y)
    }

    fun setLocationRelativeTo(component: java.awt.Component?) {
        frame.setLocationRelativeTo(component)
    }

    fun add(x: Component) {
        frame.add(x)
        frame.pack()
    }
}
