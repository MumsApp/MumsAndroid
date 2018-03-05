package com.mumsapp.android.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.mumsapp.domain.exceptions.InvalidRefreshTokenException
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class LifecyclePresenter<View: LifecycleView>: BasePresenter<View>(), LifecycleObserver {

    fun attachViewWithLifecycle(view: View) {
        attachView(view)
        this.view?.lifecycle?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        stop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        destroy()
        detachView()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        resume()
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        create()
    }

    override fun detachView() {
        super.detachView()
        view?.lifecycle?.removeObserver(this)

    }

    override fun isViewAvailable(): Boolean {
        if (view == null || view?.lifecycle == null) {
            return false
        }

        val state = view?.lifecycle?.currentState

        return state!!.isAtLeast(Lifecycle.State.CREATED) && state != Lifecycle.State.DESTROYED
    }
}