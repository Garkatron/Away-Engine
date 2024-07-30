package deus.away.engine.core.systems.saving

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.toml.TomlFactory
import java.io.File

class TomlManager {

    val tomlMapper = ObjectMapper(TomlFactory())

    fun write(path: String, value: Any) {
        val f = File(path)
        tomlMapper.writeValue(f,value)
    }

    fun read(path: String, valueType: Class<*>): Any? {
        return try {
            val file = File(path)
            tomlMapper.readValue(file, valueType)
        } catch (e: Exception) {
            println("Error reading the file: ${e.message}")
            null
        }
    }

}