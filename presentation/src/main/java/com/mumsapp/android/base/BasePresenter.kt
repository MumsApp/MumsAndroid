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

    protected open fun stop() {
        resetDisposables()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        destroy()
        detachView()
    }

    protected open fun destroy() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        pause()
    }


    protected open fun pause() {}


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        resume()
    }

    protected open fun resume() {}


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        start()
    }

    protected open fun start() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        create()
    }

    protected open fun create() {}

    fun detachView() {
        view?.getLifecycle()?.removeObserver(this)
        view = null
    }

    protected fun resetDisposables() {
        compositeDisposable?.dispose()
        compositeDisposable = null
        compositeDisposable = CompositeDisposable()
    }

    fun onGoingBack(): Boolean {
        return true
    }
}