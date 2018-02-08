package com.mumsapp.data.utils

import com.mumsapp.domain.utils.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulerProviderImpl : SchedulerProvider {

    @Inject
    constructor()

    override fun provideIOScheduler(): Scheduler = Schedulers.io()

    override fun provideComputationScheduler(): Scheduler = Schedulers.computation()

    override fun provideMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()
}