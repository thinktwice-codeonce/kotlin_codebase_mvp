package com.codebase.mvp.service.converter

import com.google.gson.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type


class LocalDateTimeConverter : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime>{

    private val FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    override fun serialize(src: LocalDateTime, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(FORMATTER.format(src))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LocalDateTime {
        return FORMATTER.parse(json.asString, LocalDateTime.FROM)
    }

}