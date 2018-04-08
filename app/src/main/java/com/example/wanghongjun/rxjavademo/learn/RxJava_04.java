package com.example.wanghongjun.rxjavademo.learn;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by wanghongjun on 2018/4/3.
 *
 * RxJava-------Disposable简介
 *
 * Disposable,这个单词意思是一次性用品。RxJava中，用
 * 它切断Observer(观察者)和Observable(被观察者)之间
 * 的连接切断，从而导致Observer(观察者)收不到事件
 *
 */

public class RxJava_04 {

    /**
     * 获取Dispoable对象-1
     */
    public static void demo_01(){
        Observer<String> observer=new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    /**
     * 获取Dispoable对象-2
     */
    public static void demo_02(){
        Disposable disposable= Observable
                .just("你好").subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });
    }
}
