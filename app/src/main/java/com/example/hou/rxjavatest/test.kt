package com.example.hou.rxjavatest

import android.util.Log

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created by hou on 2018/9/15.
 */

class test {
    private val TAG = "Main"
    private fun tests() {
        val observable1 = Observable.create(ObservableOnSubscribe<Int> { emitter ->
            var i = 0
            while (true) {
                emitter.onNext(i)
                Thread.sleep(2000)  //发送事件之后延时2秒
                i++
            }
        }).subscribeOn(Schedulers.io())

        val observable2 = Observable.create(ObservableOnSubscribe<String> { emitter -> emitter.onNext("A") }).subscribeOn(Schedulers.io())

        Observable.zip(observable1, observable2, object : BiFunction<Int, String, String> {
            @Throws(Exception::class)
            override fun apply(t1: Int, s: String): String {
                return t1.toString() + s
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe({ s: String? -> Log.d(TAG, s) }) {
            throwable -> Log.w(TAG, throwable)
        }


    }
}
