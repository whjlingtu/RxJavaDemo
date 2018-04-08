package com.example.wanghongjun.rxjavademo.learn;

import android.support.annotation.NonNull;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by wanghongjun on 2018/3/30.
 */

public class RxJava_01 {

    private static final String LOG_TAG="RxJava_01";

    /**
     * 第一RxJava：案例
     * 创建Observable的最基本方式
     */
    public static void  demo_01(){
        //被观察者
        Observable<String> observable=Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext("Hello,world");
                        e.onComplete();
                    }
                });

        //观察者
        Observer<String> observer=new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(LOG_TAG,"-----------"+d);
            }

            @Override
            public void onNext(String s) {
                Log.d(LOG_TAG,s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(LOG_TAG,"完成");
            }
        };

        //通过subscribe 使observable和observer发送订阅关系
        observable.subscribe(observer);

    }

    /**
     * just方式：创建Observable
     */
    public static void demo_02(){
        //被观察者
        Observable<String> observable=Observable
                .just("Hello");
        Observer<String> observer = getStringObserver();


        observable.subscribe(observer);
    }



    /**
     *  fromIterable:方式创建Observable
     */
    public static void demo_03(){
        List<String> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add("Hello"+i);
        }
        //被观察者
        Observable<String> observable=Observable
                .fromIterable(list);

        //观察者
        Observer<String> observer=getStringObserver();

        observable.subscribe(observer);
    }


    /**
     * defer()：方式创建Observable
     */
    public static void demo_04(){
        //被观察者
        Observable<String> observable=Observable
                .defer(new Callable<ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> call() throws Exception {
                        return Observable.just("Hello");
                    }
                });

        //观察者
        Observer observer=getStringObserver();

        observable.subscribe(observer);

    }

    /**
     * interval()方式：创建一个按固定时间间隔发射整数序列
     * 即按照固定2秒一次调用onNext()方法
     */
    public static void demo_05() {
        //被观察者模式
        Observable<Long> observable = Observable.interval(2, TimeUnit.SECONDS);
        //观察者
        Observer<Long> observer = getLongObserver();

        observable.subscribe(observer);
    }



    /**
     * range:创建一个指定整数序列的Observable,第一个参数为起始值，
     * 第二个为发送的个数，如果未0则不发送，负数则抛异常
     */
    public static void demo_06(){
        //被观察者
        Observable observable=Observable.range(1,5);
        //观察者
        Observer<Integer> observer = getIntObserver();
        observable.subscribe(observer);
    }

    /**
     * timer:创建一个Observable,它在一个给定的延迟后发射一个特殊值
     */
    public static void demo_07(){
        //被观察者
        Observable<Long> observable=Observable.timer(5,TimeUnit.SECONDS);//延迟2秒
        //观察者
        Observer<Long> observer=getLongObserver();

        observable.subscribe(observer);
    }

    /**
     * repeat:创建一个Observable,该Observable事件可以重复调用
     */
    public static void demo_08(){
        Observable<Integer> observable=Observable.just(123).repeat();
        Observer<Integer> observer=getIntObserver();

        observable.subscribe(observer);
    }


    /**
     * 实现简便式的观察者模式
     */
    public static void demo_09(){
        Observable.just("Hello").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(LOG_TAG,"-----"+s);
            }
        });
    }



//------------------------------------------------------------------------

    @NonNull
    private static Observer<Long> getLongObserver() {
        return new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(LOG_TAG, "--------" + d);
            }

            @Override
            public void onNext(Long aLong) {
                Log.d(LOG_TAG, "--------" + aLong);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(LOG_TAG, "--------" + e);
            }

            @Override
            public void onComplete() {
                Log.d(LOG_TAG, "--------" + "完成");
            }
        };
    }

    private static Observer<Integer> getIntObserver() {
        Observer<Integer> observer=new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(LOG_TAG,"----"+d);
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(LOG_TAG,"-----------"+integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(LOG_TAG,"-----------"+e);
            }

            @Override
            public void onComplete() {
                Log.d(LOG_TAG,"-----------"+"完成");
            }
        };
        return observer;
    }

    @NonNull
    private static Observer<String> getStringObserver() {
        //观察者
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(LOG_TAG,"----------"+d);
            }

            @Override
            public void onNext(String s) {
                Log.d(LOG_TAG,"----------"+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(LOG_TAG,"----------"+e);
            }

            @Override
            public void onComplete() {
                Log.d(LOG_TAG,"---------- end");
            }
        };
    }




}
