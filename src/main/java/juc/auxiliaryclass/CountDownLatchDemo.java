package juc.auxiliaryclass;

import java.util.concurrent.CountDownLatch;

/**
 * @author zy
 * @version 1.0.0
 * @ClassName CountDownLatchDemo.java
 * @Description  juc辅助类
 * 让一些线程阻塞直到另一些线程完成一系列操作后才被唤醒。
 *
 * CountDownLatch主要有两个方法，当一个或多个线程调用await方法时，这些线程会阻塞。
 * 其它线程调用countDown方法会将计数器减1(调用countDown方法的线程不会阻塞)，
 * 当计数器的值变为0时，因await方法阻塞的线程会被唤醒，继续执行。
 *
 * 解释：6个同学陆续离开教室后值班同学才可以关门。
 *
 * main主线程必须要等前面6个线程完成全部工作后，自己才能开干
 * @CreateDate 2021-03-09  23:04:25
 */
//允许一个或多个线程等待直到在其他线程中执行的一组操作完成的同步辅助。
    //await方法阻塞，直到由于countDown()方法的调用而导致当前计数达到零，之后所有等待线程被释放，并且任何后续的await 调用立即返回
public class CountDownLatchDemo {
    public static void main(String[] args) {

        CountDownLatch downLatch = new CountDownLatch(6);  //给定的计数

        for (int i = 0; i < 6; i++) {
            final int temp = i;
            new Thread(()->{
                System.out.println("第"+(temp)+"同学已经离开了教室");
                downLatch.countDown();  //每调用一次，调用改方法的线程就会进入阻塞状态，并且计数会减去1
            },String.valueOf(i)).start();
        }

        try {
            downLatch.await(); //当前计数达到零，之后所有等待线程被释放，并且任何后续的await 调用立即返回
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("同学们已经走完了，班长可以关教室门了");

    }
}


