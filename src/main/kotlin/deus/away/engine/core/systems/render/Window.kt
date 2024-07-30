package deus.away.engine.core.systems.render

import java.awt.Component
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame

class Window(title: String = "Screen Window", width: Int = 400, height: Int = 300) {
    private val frame: JFrame = JFrame(title).apply {
        setSize(width, height)
        defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE
        setLocationRelativeTo(null) // Centra la ventana en la pantalla
    }

    private var onCloseAction: (() -> Unit)? = null

    init {
        // Agregar el WindowListener al JFrame
        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                onCloseAction?.invoke() // Ejecuta la acción de cierre
            }
        })
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

    fun setLocationRelativeTo(component: Component?) {
        frame.setLocationRelativeTo(component)
    }

    fun add(component: Component) {
        frame.add(component)
        frame.pack()
    }

    fun setOnCloseAction(action: () -> Unit) {
        onCloseAction = action
    }
}
