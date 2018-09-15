package com.example.hou.rxjavatest

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.function.BiFunction
import java.util.function.Consumer

/**
 * Created by hou on 2018/9/15.
 */
class RxjavaTest {

    companion object {
        fun test(){
            var observable1 : Observable <Int> = Observable.create(object: ObservableOnSubscribe<Int>{
                override fun subscribe(emitter: ObservableEmitter<Int>) {
                    var i = 0
                    while (i<10000){
                        i++
                        emitter.onNext(i)
                        Thread.sleep(2000)
                    }
                }
            }).subscribeOn(Schedulers.io())

            var observable2 : Observable<String> = Observable.create(object :ObservableOnSubscribe<String>{

                override fun subscribe(emitter: ObservableEmitter<String>) {
                    emitter.onNext("A")
                 }

            }).subscribeOn(Schedulers.io())

            Observable.zip(observable1, observable2, object : io.reactivex.functions.BiFunction<Int, String, String> {
                @Throws(Exception::class)
                override fun apply(t1: Int, s: String): String {
                    return t1.toString() + s
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe({ s: String? -> Log.d("Main", s) }){
                throwable -> Log.w("Main", throwable)
            }

        }
    }

}