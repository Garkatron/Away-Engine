package core.signal

class Signal<T> {
    private val listeners = mutableListOf<(T) -> Unit>()

    fun connect(listener: (T) -> Unit) {
        listeners.add(listener)
    }

    fun emit(value: T) {
        for (listener in listeners) {
            listener(value)
        }
    }
}
