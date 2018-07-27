package com.mumsapp.domain.utils

import org.threeten.bp.ZonedDateTime

interface DateManager {

    fun parse(timestamp: Long): ZonedDateTime

    fun getRelativeTimeSpanString(time: ZonedDateTime): String

    fun getRelativeTimeSpanString(millis: Long, now: Long): String

    fun getCurrentTime(): ZonedDateTime

    fun formatToApiTimestamp(time: ZonedDateTime): Long
}