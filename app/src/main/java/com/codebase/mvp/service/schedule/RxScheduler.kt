package com.bss.codebase.service.schedule

import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action0
import rx.functions.Action1
import rx.schedulers.Schedulers

@Suppress("NAME_SHADOWING")
class RxScheduler {

    companion object {

        /**
         * Apply schedule for computation task
         */
        fun <T> applyLogicSchedulers(): Observable.Transformer<T, T> = Observable.Transformer { observable ->
            observable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
        }

        /**
         * Apply io schedule for calling API
         */
        fun <T> applyIoSchedulers(): Observable.Transformer<T, T> = Observable.Transformer { observable ->
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

        /**
         * Apply new thread schedule
         */
        fun <T> applyNewThreadSchedulers(): Observable.Transformer<T, T> = Observable.Transformer { observable ->
            observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        }

        /**
         * On Stop with subscription
         */
        fun onStop(subscription: Subscription?) {
            if (subscription != null && !subscription.isUnsubscribed) {
                subscription.unsubscribe()
            }
        }

        /**
         * Safe subscribe
         */
        fun <T> safeSubscribe(observable: Observable<T>,
                              onNext: Action1<in T>?,
                              onError: Action1<Throwable>?,
                              onCompleted: Action0?): Subscription {

            var onNext = onNext
            var onError = onError
            var onCompleted = onCompleted
            if (onNext == null) {
                onNext = Action1 { t -> }
            }
            if (onError == null)
                onError = Action1 { throwable -> }
            if (onCompleted == null)
                onCompleted = Action0 { }
            return observable.subscribe(onNext, onError, onCompleted)
        }
    }
}