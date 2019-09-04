package com.bss.codebase.service.converter

import com.google.gson.GsonBuilder
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.OffsetTime
import org.threeten.bp.ZonedDateTime


class ThreeTenGsonAdapter {

    companion object {
        fun registerInstant(gsonBuilder: GsonBuilder): GsonBuilder {
            return gsonBuilder.registerTypeAdapter(Instant::class.java, InstantConverter())
        }

        fun registerLocalDate(gsonBuilder: GsonBuilder): GsonBuilder {
            return gsonBuilder.registerTypeAdapter(LocalDate::class.java, LocalDateConverter())
        }

        fun registerLocalDateTime(gsonBuilder: GsonBuilder): GsonBuilder {
            return gsonBuilder.registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeConverter())
        }

        fun registerLocalTime(gsonBuilder: GsonBuilder): GsonBuilder {
            return gsonBuilder.registerTypeAdapter(LocalTime::class.java, LocalTimeConverter())
        }

        fun registerOffsetDateTime(gsonBuilder: GsonBuilder): GsonBuilder {
            return gsonBuilder.registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeConverter())
        }

        fun registerOffsetTime(gsonBuilder: GsonBuilder): GsonBuilder {
            return gsonBuilder.registerTypeAdapter(OffsetTime::class.java, OffsetTimeConverter())
        }

        fun registerZonedDateTime(gsonBuilder: GsonBuilder): GsonBuilder {
            return gsonBuilder.registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeConverter())
        }

        /**
         * A convenient method to register all supported ThreeTen BP types.
         *
         * @param gsonBuilder a GsonBuilder to be registered.
         * @return a GsonBuilder knows how to de/serialize all supported ThreeTen BP types.
         */
        fun registerAll(gsonBuilder: GsonBuilder): GsonBuilder {
            registerInstant(gsonBuilder)
            registerLocalDate(gsonBuilder)
            registerLocalDateTime(gsonBuilder)
            registerLocalTime(gsonBuilder)
            registerLocalDate(gsonBuilder)
            registerOffsetDateTime(gsonBuilder)
            registerOffsetTime(gsonBuilder)
            registerZonedDateTime(gsonBuilder)

            return gsonBuilder
        }
    }
}