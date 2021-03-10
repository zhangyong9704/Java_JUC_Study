package juc.auxiliaryclass;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author zy
 * @version 1.0.0
 * @ClassName SemaphoreDemo.java
 * @Description
 * @CreateDate 2021-03-10  22:42:05
*  在信号量上我们定义两种操作：
 * acquire（获取） 当一个线程调用acquire操作时，它要么通过成功获取信号量（信号量减1），
 *       要么一直等下去，直到有线程释放信号量，或超时。
 *   release（释放）实际上会将信号量的值加1，然后唤醒等待的线程。
 *
 * 信号量主要用于两个目的，一个是用于多个共享资源的互斥使用，另一个用于并发线程数的控制。
 *
 * 题目：四个停车位，六辆车抢车位,有一个退出，没进入的就需要抢占，先进的需要完成退出后才可以抢占
 */
public class SemaphoreDemo {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(4);   //四个信号灯 相当于四个车位通知器

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();  //抢占信号
                    System.out.println(Thread.currentThread().getName() + "抢到车位了");
                    TimeUnit.SECONDS.sleep(2);  //睡眠两秒
                    System.out.println(Thread.currentThread().getName() + "已经退出车位了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();   //通知释放
                }
            }).start();


        }

    }
}
