package com.example.wanghongjun.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wanghongjun.rxjavademo.learn.RxJava_01;
import com.example.wanghongjun.rxjavademo.learn.RxJava_02;
import com.example.wanghongjun.rxjavademo.learn.RxJava_03;
import com.example.wanghongjun.rxjavademo.learn.RxJava_05;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //rxjava_01();
        //rxjava_02();
       // rxjava_03();

        rxjava_05();
    }

    private void rxjava_01(){
        //RxJava_01.demo_01();    //第一RxJava程序
        //RxJava_01.demo_02();    //just
        //RxJava_01.demo_03();    //fromIteralbe
        //RxJava_01.demo_04();    //defer
        //RxJava_01.demo_05();    //interval
        //RxJava_01.demo_06();    //range
        //RxJava_01.demo_07();    //timer
        //RxJava_01.demo_08();    //repeat()
       // RxJava_01.demo_09();    //简便式观察者
    }


    private void rxjava_02(){
       // RxJava_02.demo_01();    //map操作符
       // RxJava_02.demo_02();    //flatMap操作符
       // RxJava_02.demo_03();    //flat操作符
        //RxJava_02.demo_04();    //take操作符
       // RxJava_02.demo_05();    //doOnNext操作符
    }

    private void rxjava_03(){
        RxJava_03.demo_01();    //使用Scheduler样例
    }

    private void rxjava_05(){
        //RxJava_05.demo_01();    //Flowalbe
        //RxJava_05.demo_02();    //ERROR
       // RxJava_05.demo_03();    //Buffer
       // RxJava_05.demo_04();    //DROP
        RxJava_05.demo_05();    //LATEST
    }

}
