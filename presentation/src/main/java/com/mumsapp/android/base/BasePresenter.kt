package com.mumsapp.android.base

import com.mumsapp.domain.exceptions.InvalidRefreshTokenException
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<View: BaseView> {

    protected var view: View? = null

    protected var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    fun attachView(view: View) {
        this.view = view
    }

    open fun detachView() {
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

    protected open fun isViewAvailable(): Boolean {
        return view != null
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

    protected open fun stop() {
        resetDisposables()
    }

    protected open fun destroy() {

    }

    protected open fun pause() {}

    protected open fun resume() {}

    protected open fun start() {}

    protected open fun create() {}
}