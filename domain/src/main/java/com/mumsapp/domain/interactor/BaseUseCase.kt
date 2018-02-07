package com.mumsapp.domain.interactor

import com.mumsapp.domain.executor.SchedulerProvider
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.model.BaseRequest
import com.mumsapp.domain.model.BaseResponse
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.Action

abstract class BaseUseCase<Request : BaseRequest, Response : BaseResponse> {

    protected val observablesMap: MutableMap<Request, Observable<Response>> = HashMap()

    var cacheObservable = false
    private val schedulerProvider: SchedulerProvider
    private lateinit var useCaseTransformerProvider: UseCaseTransformerProvider

    constructor(schedulerProvider: SchedulerProvider) {
        this.schedulerProvider = schedulerProvider
    }

    constructor(schedulerProvider: SchedulerProvider,
                useCaseTransformerProvider: UseCaseTransformerProvider) : this(schedulerProvider) {
        this.useCaseTransformerProvider = useCaseTransformerProvider
    }

    abstract fun createUseCaseObservable(param: Request): Observable<Response>

    fun isRunning(param: Request): Boolean {
        return observablesMap.containsKey(param)
    }

    fun getRunningCount(): Int {
        return observablesMap.size
    }

    fun execute(param: Request): Observable<Response> {
        var observable = observablesMap[param]

        if (observable == null || !cacheObservable) {
            observable = createUseCaseObservable(param)
            observable = composeIfNotNull(observable, useCaseTransformerProvider)
                    .subscribeOn(choseSubscribeOnScheduler(schedulerProvider))
                    .observeOn(choseObserveOnScheduler(schedulerProvider))
                    .doOnDispose(OnTerminateAction(param))

            observablesMap.put(param, observable)
        }

        return observable!!
    }

    private fun composeIfNotNull(observable: Observable<Response>, transformer: UseCaseTransformerProvider?): Observable<Response> {
        return if (transformer != null) {
            observable.compose(transformer.get())
        } else observable
    }

    protected fun choseSubscribeOnScheduler(provider: SchedulerProvider): Scheduler {
        return provider.provideIOScheduler()
    }

    protected fun choseObserveOnScheduler(provider: SchedulerProvider): Scheduler {
        return provider.provideMainThreadScheduler()
    }

    protected fun <X> toColdObservable(x: X): Observable<X> {
        return Observable.defer { Observable.just(x) }
    }

    private inner class OnTerminateAction internal constructor(private val param: Request) : Action {

        @Throws(Exception::class)
        override fun run() {
            observablesMap.remove(param)
        }
    }
}