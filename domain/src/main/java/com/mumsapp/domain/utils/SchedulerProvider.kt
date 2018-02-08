package com.mumsapp.domain.utils

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun provideIOScheduler(): Scheduler

    fun provideComputationScheduler(): Scheduler

    fun provideMainThreadScheduler(): Scheduler
}