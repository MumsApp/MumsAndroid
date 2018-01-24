package com.mumsapp.android.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<View: BaseView>: LifecycleObserver {

    protected var view: View? = null

    protected var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    fun attachViewWithLifecycle(view: View) {
        this.view = view
        this.view?.getLifecycle()?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        stop()
    }

    protected fun stop() {
        resetDisposables()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        destroy()
        detachView()
    }

    protected fun destroy() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        pause()
    }


    protected fun pause() {}


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        resume()
    }

    protected fun resume() {}


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        start()
    }

    protected fun start() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        create()
    }

    protected fun create() {}

    fun detachView() {
        view?.getLifecycle()?.removeObserver(this)
        view = null
    }

    protected fun resetDisposables() {
        compositeDisposable?.dispose()
        compositeDisposable = null
        compositeDisposable = CompositeDisposable()
    }
}