package juc.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 内容摘要:
 * <p>文件名称: SaleTicket.java
 * <p>版权所有: 版权所有(C)2015-2020
 *
 * @Version v1.0
 * @Author Administrator
 * @Date 2021-03-09 -- 10:03
 * @Description TODO
 *
 *
 *  *题目：三个售票员   卖出   30张票
 *  笔记：如何编写企业级的多线程
 *  * 固定的编程套路+模板
 *  1.在高内聚低耦合的前提下，线程    操作(对外暴露的调用方法)     资源类
 *    1.1先创建一个资源类
 *    1.2多线程操作资源一定要加锁
 */

public class SaleTicketLock {
    public static void main(String[] args) {
        final Ticket tickets = new Ticket();
        new Thread(()->{ for (int i = 0; i < 10 ; i++)  tickets.saleTickets(); },"A").start();
        new Thread(()->{ for (int i = 0; i < 10 ; i++)  tickets.saleTickets(); },"B").start();
        new Thread(()->{ for (int i = 0; i < 10 ; i++)  tickets.saleTickets(); },"C").start();
    }


}


class Ticket {
    private int ticket = 30;  //现有30张票

    private Lock lock = new ReentrantLock();

    public void  saleTickets(){
        lock.lock();
        try {
            if (ticket>0){
                System.out.println(Thread.currentThread().getName()+"卖出了第"+(ticket--)+"张票，剩余"+ticket+"张");
            }else{
                System.out.println("票已售罄...");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}