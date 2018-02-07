package com.mumsapp.android.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.mumsapp.domain.exceptions.InvalidRefreshTokenException
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

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

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable?.add(disposable)
    }

    protected fun resetDisposables() {
        compositeDisposable?.dispose()
        compositeDisposable = null
        compositeDisposable = CompositeDisposable()
    }

    fun onGoingBack(): Boolean {
        if(view == null) {
            return true
        }

        if(isViewAvailable() && view!!.isLoadingPresented()) {
            return false
        }

        return true
    }

    protected fun isViewAvailable(): Boolean {
        if (view == null || view?.lifecycle == null) {
            return false
        }

        val state = view?.getLifecycle()?.currentState

        return state!!.isAtLeast(Lifecycle.State.STARTED) && state != Lifecycle.State.DESTROYED
    }

    protected fun <T> applyOverlaysToObservable(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.doOnSubscribe({ tryShowLoading() })
                    .doOnComplete({ tryHideOverlays() })
                    .onErrorResumeNext({ throwable: Throwable ->
                        if(throwable is InvalidRefreshTokenException) {
                            //view.showSessionExpired()
                            Observable.empty<T>()
                        }

                        Observable.error<T>(throwable)

                    }).doOnError({ tryHideOverlays() })
        }
    }

    private fun tryShowLoading() {
        if (isViewAvailable()) {
            view?.showLoading()
        }
    }

    private fun tryHideOverlays() {
        if (isViewAvailable()) {
            view?.hideOverlays()
        }
    }
}