package juc.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zy
 * @version 1.0.0
 * @ClassName ReadWriteLockDemo.java
 * @Description
 * @CreateDate 2021-03-10  22:56:21
 *
 * 读写锁
 *  对读的部分开放  对写操作部分限制加锁
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();  //未加锁出现了读写事务异常
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            new Thread(()->{
                myCache.put(num+"",num+"");
            },String.valueOf(i)).start();
        }
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            new Thread(()->{
                myCache.get(num+"");
            },String.valueOf(i)).start();
        }
    }

}

/**
 * 模拟创建一个缓存的类，拥有读写两个功能
 */
class MyCache{
    private volatile Map<String,Object> map = new HashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    public void put(String key,Object value){
        try {
            readWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName()+"\t 正在写"+key);
            //暂停一会儿线程
            try {
                TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException e) {e.printStackTrace(); }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写完了"+key);
        }catch (Exception e){
            e.getStackTrace();
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public Object get(String key) {
        Object result = null;
        try {
            readWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + "\t 正在读" + key);
            TimeUnit.MILLISECONDS.sleep(300);
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读完了" + result);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return result;
    }
}
