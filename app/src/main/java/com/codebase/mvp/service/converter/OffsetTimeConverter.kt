package com.bss.codebase.service.converter

import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import org.threeten.bp.OffsetTime
import com.google.gson.JsonParseException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type

class OffsetTimeConverter : JsonSerializer<OffsetTime>, JsonDeserializer<OffsetTime> {

    private val FORMATTER = DateTimeFormatter.ISO_OFFSET_TIME

    override fun serialize(src: OffsetTime, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(FORMATTER.format(src))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): OffsetTime {
        return FORMATTER.parse(json.asString, OffsetTime.FROM)
    }
}