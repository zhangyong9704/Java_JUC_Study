package juc.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 内容摘要:
 * <p>文件名称: Producer.java
 * <p>修改记录: ...</li>
 * <p>其他说明: ...</li>
 *
 * @Version v1.0
 * @Author Administrator
 * @Date 2021-03-09 -- 10:49
 * @Description TODO
 *
 * /**
 *  * 题目：现在两个线程，可以操作初始值为零的一个变量，
 *  * 实现一个线程对该变量加1，一个线程对该变量-1，
 *  * 实现交替，来10轮，变量初始值为0.
 *  *      1.高内聚低耦合前提下，线程操作资源类
 *  *      2.判断/干活/通知
 *  *      3.防止虚假唤醒(判断只能用while，不能用if)
 *  * 知识小总结：多线程编程套路+while判断+新版写法
 */
public class ProducerLock {
    public static void main(String[] args) {
        SourcesLock sources = new SourcesLock();

        for (int i = 0; i < 10 ; i++) {
            new Thread(()->{
                try {
                    sources.increase();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

        for (int i = 0; i < 10 ; i++) {
            new Thread(()->{
                try {
                    sources.des();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

    }

}

class SourcesLock{
    int num = 0;  //初始值
    private Lock lock = new ReentrantLock();  //创建锁
    private Condition condition = lock.newCondition();  //产生lock锁的钥匙

    public  void increase() throws InterruptedException {
        lock.lock();
        try {
            //判断
            while (num!=0){  //使用while时，重新唤醒会重新判读当前条件，if不会再重新判断条件
                condition.await();  //调用wait()  并不会让线程退出去，只是停留在当前执行的位置，唤醒后会继续原位置执行
            }
            //干活
            num++;
            System.out.println("线程名称"+Thread.currentThread().getName()+":"+num);

            //通知
            condition.signalAll();  //唤醒等待的线程
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();  //释放锁
        }
    }

    public synchronized void des() throws InterruptedException {
        lock.lock();
        try {
            //判断
            while (num==0){
                condition.await();  //等待
            }
            //干活
            num--;
            System.out.println("线程名称"+Thread.currentThread().getName()+":"+num);

            //通知
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
