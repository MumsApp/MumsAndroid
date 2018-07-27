package com.mumsapp.data.utils

import android.text.format.DateUtils
import com.mumsapp.domain.utils.DateManager
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class DateManagerImpl : DateManager {

    @Inject
    constructor()

    override fun parse(timestamp: Long): ZonedDateTime {
        val instant = Instant.ofEpochSecond(timestamp)
        return ZonedDateTime.ofInstant(instant, getLocalTimeZone())
    }

    override fun getRelativeTimeSpanString(time: ZonedDateTime): String {
        val millis = time.toInstant().toEpochMilli()
        val now = getCurrentTime().toInstant().toEpochMilli()

        return getRelativeTimeSpanString(millis, now)
    }

    override fun getRelativeTimeSpanString(millis: Long, now: Long): String {
        val label = DateUtils.getRelativeTimeSpanString(millis, now, DateUtils.MINUTE_IN_MILLIS)
        return label.toString()
    }

    override fun getCurrentTime() = ZonedDateTime.now()

    override fun formatToApiTimestamp(time: ZonedDateTime): Long {
        return time.withZoneSameInstant(getUTCTimeZone()).toInstant().toEpochMilli()
    }

    private fun getUTCTimeZone() = ZoneId.of("UTC")

    private fun getLocalTimeZone() = ZoneId.systemDefault()
}