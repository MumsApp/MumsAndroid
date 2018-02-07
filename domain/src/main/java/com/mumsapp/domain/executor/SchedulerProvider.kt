package com.mumsapp.domain.executor

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun provideIOScheduler(): Scheduler

    fun provideComputationScheduler(): Scheduler

    fun provideMainThreadScheduler(): Scheduler
}