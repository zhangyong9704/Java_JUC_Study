package juc.example;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 内容摘要:
 * <p>文件名称: CollectionNotSafe.java
 * <p>公　　司: 北京赛福阔利特科技有限公司
 * <p>版权所有: 版权所有(C)2015-2020
 * <p>修改记录: ...</li>
 * <p>其他说明: ...</li>
 *
 * @Version v1.0
 * @Author Administrator
 * @Date 2021-03-09 -- 14:14
 * @Description TODO
 *
 * 通常情况下的集合都是不安全的
 *
 * 1.故障现象
 * 并发修改异常
 * java.util.ConcurrentModificationException
 * 2.导致原因
 * 3.解决方法
 *      3.1 new Vector<>();
 *      3.2 Collections.synchronizedList(new ArrayList<String>());
 *      3.3 new CopyOnWriteArrayList(); //写时复制
 * 4.优化建议(同样的错误不犯第二次)
 *
 * 写时复制：
 *  CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器Object[]添加，而是现将当前容器Object[]进行Copy，
 *  复制出一个新的容器Object[] newElements，然后新的容器Object[] newElements里添加元素，添加完元素之后，
 *  再将原容器的引用指向新的容器setArray(newElements);。这样做的好处是可以对CopyOnWrite容器进行并发的读，
 *  而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器
 */

public class CollectionNotSafe {

    public static void main(String[] args) {
          //异常情况1
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 100 ; i++) {
//            new Thread(()->{
//                list.add(UUID.randomUUID().toString());
//                System.out.println(list);  //Exception in thread "54" java.util.ConcurrentModificationException
//            },String.valueOf(i)).start();
//        }


        //线程安全的方式一
        List<String> list1 = new Vector<>();  //可以达到线程安全，但是底层用了synchronized，比较浪费效率

        //线程安全的方式二
        List<String> list2 = Collections.synchronizedList(new ArrayList<>());  //这种方式也是可以达到线程安全，但是也是用了synchronized，比较浪费效率

        //线程安全的方式三
        List<String> list3 = new CopyOnWriteArrayList<>();  //写时复制Arraylist底层使用Lock = new ReentrantLock() 方式加锁，效率高，线程安全
//        for (int i = 0; i < 100 ; i++) {
//            new Thread(()->{
//                list3.add(UUID.randomUUID().toString().substring(0,8));
//                System.out.println(list3);  //Exception in thread "54" java.util.ConcurrentModificationException
//            },String.valueOf(i)).start();
//        }

        Set<String> set = new HashSet<>();
//        for (int i = 0; i < 100 ; i++) {
//            new Thread(()->{
//                set.add(UUID.randomUUID().toString());
//                System.out.println(set);  //Exception in thread "54" java.util.ConcurrentModificationException
//            },String.valueOf(i)).start();
//        }

        Set<String> set1 = new CopyOnWriteArraySet<>();
//        for (int i = 0; i < 100 ; i++) {
//            new Thread(()->{
//                set1.add(UUID.randomUUID().toString());
//                System.out.println(set1);  //Exception in thread "54" java.util.ConcurrentModificationException
//            },String.valueOf(i)).start();
//        }

        Map<String,String> map = new HashMap<>();
//        for (int i = 0; i < 100 ; i++) {
//            new Thread(()->{
//                map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
//                System.out.println(map);  //Exception in thread "54" java.util.ConcurrentModificationException
//            },String.valueOf(i)).start();
//        }

        Map<String,String> map1 = new ConcurrentHashMap<>();
        for (int i = 0; i < 100 ; i++) {
            new Thread(()->{
                map1.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
                System.out.println(map1);  //Exception in thread "54" java.util.ConcurrentModificationException
            },String.valueOf(i)).start();
        }


    }

}
