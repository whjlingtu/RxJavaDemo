package com.example.wanghongjun.rxjavademo.learn;


import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wanghongjun on 2018/4/3.
 *
 * RxJava 线程调度器-----Schedule
 *
 * 在不指定的情况下，RxJava遵循的是线程不变的原则，即：
 * 在那个线程调用subscibe(),就在哪个线程生产事件；在哪个
 * 线程生产事件，就在哪个线程消费事件。如果需要切换线程，
 * 需要用到Scheduler(调度器)
 *
 * API:
 *
 *Schedules.immediate():指定在当前线程运行，相当于不指定线程
 * 这是默认的Schedules
 *
 * Schedules.newThread():总是启用新线程，并在新线程中执行操作
 *
 * Schedules.io():I/O 操作(读写文件,读写数据库,网络信息交互等)
 * 所使用的schedules.行为模式和newThread()差不多，区别在于io()
 * 的内部实现一个无数量上限的线程池,可以重用空闲的线程，因此多数
 * 情况下io()比newThread()更有效率，不要把计算工作放在io中，可以
 * 避免创建不必要的线程
 *
 * Schedules.computation():计算所使用的Scheduler.这个计算指的是
 * CPU密集型计算，即不会被I/O等操作限制性能的操作,例如图形的计算。这
 * 个Scheduler使用固定的线程池，大小为CPU核数。不要把I/O操作放在
 * computation()中，否则I/O操作的等待时间会浪费CPU
 *
 * Andorid还有一个专用AndroidSchedulers.mainThread(),它指定
 * 将在Andorid主线程运行
 *
 *  subscribeOn():指定Observable(被观察者)所在线程，或者叫做
 *  事情产生的线程
 *
 *  observeOn():指定Observer(观察者)所在运行线程，或者叫做
 *  事情消费的线程
 *
 */

public class RxJava_03 {

    private static String LOG_TAG="RxJava_03";

    /**
     * 线程调度的使用
     */
    public static void demo_01(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(LOG_TAG,"所在线程"+Thread.currentThread().getName());
                Log.d(LOG_TAG,"发送数据"+1);

                e.onNext(1);

            }
        }).subscribeOn(Schedulers.io())     //指定事情生产所在线程
                .observeOn(AndroidSchedulers.mainThread())  //事情消费所在的线程
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(LOG_TAG,"所在线程"+Thread.currentThread().getName());
                        Log.d(LOG_TAG,"接受数据"+integer);
                    }
                });
    }
}
