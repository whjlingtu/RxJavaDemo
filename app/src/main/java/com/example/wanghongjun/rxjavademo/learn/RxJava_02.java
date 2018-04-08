package com.example.wanghongjun.rxjavademo.learn;


import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by wanghongjun on 2018/4/3.
 * RxJava 操作符
 */

public class RxJava_02 {

    private static final String LOG_TAG="RxJava_02";

    /**
     * map()操作符：就是把原来的Observable对象
     * 转换成另一个Observable对象，同时将传输的数据
     * 进行一些灵活的操作
     */
    public static void demo_01(){
        Observable<Integer> observable=
                Observable.just("Hello").map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.length();
                    }
                });

        Observer<Integer> observer = getIntegerObserver();
        observable.subscribe(observer);

    }

    /**
     * flatMap操作符：数据转换比map()更加彻底，如果发送的数据集合
     * flatmap()重新生成一个Obserable对象，并把数据转换成Observer
     * 想要的数据形式。
     */
    public static void demo_02(){
        List<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        final Observable observable=Observable.just(list)
                .flatMap(new Function<List<String>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<String> strings) throws Exception {
                        return Observable.fromIterable(strings);
                    }
                });

        Observer<String> observer = getStringObserver();

        observable.subscribe(observer);

    }

    /**
     * filter:根据正则表达式，过滤自己想要的数据加入相应的逻辑
     * 判断，返回true,则表示数据满足条件，返回false则表示数据
     * 需要被过滤，最后过滤出的数据需加入到新的Observable中，
     * 方便传递给Observerd想要的数据形式
     */
    public static  void demo_03(){
        List<String> list=new ArrayList<>();
        list.add("10009999999");
        list.add("222220000000");
        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
        }).filter(new Predicate<Object>() {
            @Override
            public boolean test(Object o) throws Exception {
                String newStr=(String)o;

                if(newStr.charAt(5)-'0'>5){
                    return true;
                }

                return false;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object s) throws Exception {
                Log.d(LOG_TAG,"-----"+(String)s);
            }
        });

    }


    /**
     * take：输出最多指定数量的结果
     */
    public static  void demo_04(){
        final List<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(list);
            }
        }).take(5).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.d(LOG_TAG,"-----"+(String)o);
            }
        });
    }

    /**
     * doOnNext:允许输出一个元素之前做一些额外的事情
     */
    public static void demo_05(){
        final List<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");

        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
        }).take(5).doOnNext(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.d(LOG_TAG,"准备工作"+(String)o);
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.d(LOG_TAG,"---"+(String)o);
            }
        });
    }

    //----------------------------------------------
    @NonNull
    private static Observer<String> getStringObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(LOG_TAG,"-----"+d);
            }

            @Override
            public void onNext(String s) {
                Log.d(LOG_TAG,"-----"+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(LOG_TAG,"-----"+e);
            }

            @Override
            public void onComplete() {
                Log.d(LOG_TAG,"-----"+"完成");
            }
        };
    }
    private static Observer<Integer> getIntegerObserver() {
        Observer<Integer> observer=new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(LOG_TAG,"----"+d);
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(LOG_TAG,"----"+integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(LOG_TAG,"----"+e);
            }

            @Override
            public void onComplete() {
                Log.d(LOG_TAG,"----"+"完成");
            }
        };
        return observer;
    }
}
