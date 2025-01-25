package com.store

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StringDeserializer
import java.io.IOException

class StrictStringDeserializer : StringDeserializer() {
    @Throws(IOException::class)
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): String? {
        val token = p.currentToken()
        if (token != JsonToken.VALUE_STRING) {
            ctxt.reportInputMismatch<Any>(String::class.java, "`%s` is not a `String` value!", p.getText())
            return null
        }
        return super.deserialize(p, ctxt)
    }
}