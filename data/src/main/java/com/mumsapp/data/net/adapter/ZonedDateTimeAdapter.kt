package com.mumsapp.data.net.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.mumsapp.domain.utils.DateManager
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class ZonedDateTimeAdapter : TypeAdapter<ZonedDateTime> {

    private val dateManager: DateManager

    @Inject
    constructor(dateManager: DateManager) {
        this.dateManager = dateManager
    }

    override fun write(out: JsonWriter?, value: ZonedDateTime?) {
        if(value == null) {
            out?.nullValue()
            return
        }

        out?.value(dateManager.formatToApiTimestamp(value))
    }

    override fun read(input: JsonReader?): ZonedDateTime? {
        if(input?.peek() == JsonToken.NULL) {
            input.nextNull()
            return null
        }

        val dateAsLong = input?.nextLong()
        return dateManager.parse(dateAsLong!!)
    }
}