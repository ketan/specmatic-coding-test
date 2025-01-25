package com.store

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.databind.json.JsonMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class StrictStringDeserializerTest {
    private val deserializationContext: DeserializationContext = JsonMapper.builder().build().deserializationContext

    @Test
    fun deserializeValidString() {
        assertEquals(deserialize("\"validString\""), "validString")
    }

    @Test
    fun deserializeEmptyString() {
        assertEquals(deserialize("\"\""), "")
    }

    @Test
    fun shouldNotDeserializeNullValue() {
        val exception = assertThrows(MismatchedInputException::class.java) {
            deserialize("null")
        }
        assertEquals("`null` is not a `String` value!", exception.message)
    }

    @Test
    fun shouldNotDeserializeBooleanValue() {
        val exception = assertThrows(MismatchedInputException::class.java) {
            deserialize("true")
        }
        assertEquals("`true` is not a `String` value!", exception.message)
    }

    @Test
    fun shouldNotDeserializeNumericValue() {
        val exception = assertThrows(MismatchedInputException::class.java) {
            deserialize("42")
        }
        assertEquals("`42` is not a `String` value!", exception.message)
    }

    @Test
    fun shouldNotDeserializeArrayValue() {
        val exception = assertThrows(MismatchedInputException::class.java) {
            deserialize("[1, 2, 3]")
        }
        assertEquals(
            "`[` is not a `String` value!", exception.message
        ) // Maybe improve this error message somehow. But may require parsing further tokens in the JSON documents.
    }

    private fun createParser(content: String): JsonParser {
        val jsonFactory = JsonFactory.builder().build()
        return jsonFactory.createParser(content)
    }

    private fun deserialize(content: String): String? {
        val parser = createParser(content)
        parser.nextToken()
        val deserializer = StrictStringDeserializer()
        return deserializer.deserialize(parser, deserializationContext)
    }
}
