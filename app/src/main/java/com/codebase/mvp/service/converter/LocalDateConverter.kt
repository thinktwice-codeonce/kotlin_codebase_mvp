package com.bss.codebase.service.converter

import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import org.threeten.bp.LocalDate
import com.google.gson.JsonParseException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type


class LocalDateConverter : JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    private val FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE

    override fun serialize(src: LocalDate, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(FORMATTER.format(src))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LocalDate {
        return FORMATTER.parse(json.asString, LocalDate.FROM)
    }
}