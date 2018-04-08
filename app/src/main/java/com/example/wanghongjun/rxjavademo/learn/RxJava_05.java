package com.example.wanghongjun.rxjavademo.learn;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wanghongjun on 2018/4/3.
 *
 *  RxJava-----Flowable
 *
 *  Flowable:解决生产事件速度大于操作消费事件的问题，
 *  这一问题：叫背压
 *
 *  Flowable:是一个被观察者，与Subsciber(观察者)配合使用，
 *  解决Backpressure(背压)问题
 */

public class RxJava_05{

    private static String LOG_TAG="RxJava_05";

    /**
     * ERROR:这种方式会产生Backpressure问题的时候直接抛出一个
     * 异常
     */
    public static void demo_01(){

        Flowable<Integer> flowable=Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> e) throws Exception {

                        Log.d(LOG_TAG,"emit 1");
                        e.onNext(1);
                        Log.d(LOG_TAG,"emit 2");
                        e.onNext(2);
                        Log.d(LOG_TAG,"emit 3");
                        e.onNext(3);
                        Log.d(LOG_TAG,"emit 4");
                        e.onNext(4);

                        e.onComplete();

                    }
                }, BackpressureStrategy.ERROR);


        Subscriber<Integer> subscriber=new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(LOG_TAG, "onSubscribe");
                //消费者向生产者申请处理事件的数量
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {

                Log.d(LOG_TAG, "onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                Log.d(LOG_TAG, "onNext: " + t);
            }

            @Override
            public void onComplete() {
                Log.d(LOG_TAG, "onComplete " );
            }
        };


        flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * 在异步调用，RxJava中有个缓存池，用来缓存消费者处理不了暂时缓存下来的数据
     * ，缓存池的默认值大小为128，即只能缓存128个事件。无论request()中传入的数字
     * 比128大或小，缓存池在刚开始都会存入128个事件。当然如果本身并没有这么多
     * 事件需要发送，则不会存128个事件
     */
    public static void demo_02(){

        Flowable<Integer> flowable=Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> e) throws Exception {

                        for(int i=0;i<129;i++){
                            e.onNext(1);
                        }

                        e.onComplete();

                    }
                }, BackpressureStrategy.ERROR);


        Subscriber<Integer> subscriber=new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(LOG_TAG, "onSubscribe");
                //消费者向生产者申请处理事件的数量
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {

                Log.d(LOG_TAG, "onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                Log.d(LOG_TAG, "onNext: " + t);
            }

            @Override
            public void onComplete() {
                Log.d(LOG_TAG, "onComplete " );
            }
        };


        flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * BUFFER:
     *  就是把RxJava中默认的只能存128个事件的缓存池换成一个大的缓冲池。
     *  支持存很多数据，
     *  这样，消费者通过request()即使传入一个很大的数字，生产者也会生产事件
     *  ，并将处理不了的事件缓存
     *
     *  当这个生产方式比较消耗内存
     */
    public static void demo_03(){
        Flowable<Integer> flowable=Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> e) throws Exception {

                        for(int i=0;i<129;i++){
                            e.onNext(i);
                        }

                        e.onComplete();

                    }
                }, BackpressureStrategy.BUFFER);


        Subscriber<Integer> subscriber=new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(LOG_TAG, "onSubscribe");
                //消费者向生产者申请处理事件的数量
                s.request(129);
            }

            @Override
            public void onNext(Integer integer) {

                Log.d(LOG_TAG, "onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                Log.d(LOG_TAG, "onNext: " + t);
            }

            @Override
            public void onComplete() {
                Log.d(LOG_TAG, "onComplete " );
            }
        };


        flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * DROP:
     *      当消费者处理不了，就丢弃。
     */
    public static void demo_04(){
        Flowable<Integer> flowable=Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> e) throws Exception {

                        for(int i=0;i<129;i++){
                            e.onNext(i);
                        }

                        e.onComplete();

                    }
                }, BackpressureStrategy.DROP);


        Subscriber<Integer> subscriber=new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(LOG_TAG, "onSubscribe");
                //消费者向生产者申请处理事件的数量
                s.request(20);
            }

            @Override
            public void onNext(Integer integer) {

                Log.d(LOG_TAG, "onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                Log.d(LOG_TAG, "onNext: " + t);
            }

            @Override
            public void onComplete() {
                Log.d(LOG_TAG, "onComplete " );
            }
        };


        flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * LATEST与DROP功能基本一致
     *
     * 消费者通过request()传入其需求n,然后生产者把n个事件传递给
     * 消费者供其消费，其他消费不掉的事件就丢掉
     *
     * 唯一的区别：LATEST总能使消费者能够接受到生产者产生的最后一个事件
     */
    public static void demo_05(){
        Flowable<Integer> flowable=Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> e) throws Exception {

                        for(int i=0;i<129;i++){
                            e.onNext(i);
                        }

                        e.onComplete();

                    }
                }, BackpressureStrategy.LATEST);


        Subscriber<Integer> subscriber=new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(LOG_TAG, "onSubscribe");
                //消费者向生产者申请处理事件的数量
                s.request(20);
            }

            @Override
            public void onNext(Integer integer) {

                Log.d(LOG_TAG, "onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                Log.d(LOG_TAG, "onNext: " + t);
            }

            @Override
            public void onComplete() {
                Log.d(LOG_TAG, "onComplete " );
            }
        };


        flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}



