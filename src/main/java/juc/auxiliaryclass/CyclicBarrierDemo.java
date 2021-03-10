package juc.auxiliaryclass;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author zy
 * @version 1.0.0
 * @ClassName CyclicBarrierDemo.java
 * @Description  循环栅栏
 * @CreateDate 2021-03-10  22:21:41
 *
 * CyclicBarrier
 * 的字面意思是可循环（Cyclic）使用的屏障（Barrier）。它要做的事情是，
 * 让一组线程到达一个屏障（也可以叫同步点）时被阻塞，
 * 直到最后一个线程到达屏障时，屏障才会开门，所有
 * 被屏障拦截的线程才会继续干活。
 * 线程进入屏障通过CyclicBarrier的await()方法。
 *
 *  题目：集齐7颗龙珠就可以召唤神龙
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(7);

        for (int i = 1; i <=7 ; i++) {
            int temp = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"收集了第"+temp+"颗龙珠");
                try {
                    cyclicBarrier.await();  //保存七次
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        cyclicBarrier.await(); //再次调用，当给定数量的线程（线程）等待它时，它将跳闸，并且当屏障跳闸时不执行预定义的动作。
        System.out.println("七颗龙珠已经收集齐，立刻召唤神龙");  //要求当前需要等待前面龙珠集齐才可以召唤神龙


        //方式而二：
        CyclicBarrier cyclicBarrier1 = new CyclicBarrier(7,()->{
            System.out.println("七颗龙珠已经收集齐，立刻召唤神龙");  //要求当前需要等待前面龙珠集齐才可以召唤神龙
        });

        for (int i = 1; i <=7 ; i++) {
            int temp = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"收集了第"+temp+"颗龙珠");
                try {
                    cyclicBarrier1.await();  //保存七次
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
