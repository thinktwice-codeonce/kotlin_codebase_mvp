package com.codebase.mvp.service.converter

import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import org.threeten.bp.LocalTime
import com.google.gson.JsonParseException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type


class LocalTimeConverter : JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

    private val FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME

    override fun serialize(src: LocalTime, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(FORMATTER.format(src))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LocalTime {
        return FORMATTER.parse(json.asString, LocalTime.FROM)
    }

}