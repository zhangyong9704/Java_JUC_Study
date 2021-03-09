package juc.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 内容摘要:
 * <p>文件名称: ConditionDemo.java
 * <p>版权所有: 版权所有(C)2015-2020
 * <p>修改记录: ...</li>
 * <p>其他说明: ...</li>
 *
 * @Version v1.0
 * @Author Administrator
 * @Date 2021-03-09 -- 11:30
 * @Description TODO
 *
 *  * 备注：多线程之间按顺序调用，实现A->B->C
 *  * 三个线程启动，要求如下：
 *  * AA打印5次，BB打印10次，CC打印15次
 *  * 接着
 *  * AA打印5次，BB打印10次，CC打印15次
 *  * 来10轮
 *  *      1.高内聚低耦合前提下，线程操作资源类
 *  *      2.判断/干活/通知
 *  *      3.多线程交互中，防止虚假唤醒(判断只能用while，不能用if)
 *  *      4.标志位
 */
public class ConditionDemo {
    public static void main(String[] args) {
        OrderSequence orderSequence = new OrderSequence();

        new Thread(()->{
            orderSequence.print1(1,5);
        },"A").start();

        new Thread(()->{
            orderSequence.print1(2,10);
        },"B").start();

        new Thread(()->{
            orderSequence.print1(3,15);
        },"C").start();

    }

}

class OrderSequence{

    private int flag = 1;  //A：1 B：2 C：3
    private Lock  lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void print1(Integer index,Integer integer){
        lock.lock();
        try {
            while (flag != index){
                condition.await();
            }
            for (int i = 0; i < integer ; i++) {
                System.out.println("线程"+Thread.currentThread().getName()+"打印了第"+(i+1)+"次");
            }
            flag++;
            condition.signal();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
}
