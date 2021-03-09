package juc.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 内容摘要:
 * <p>文件名称: CallableDemo7.java
 * <p>版权所有: 版权所有(C)2015-2020
 * <p>修改记录: ...</li>
 * <p>其他说明: ...</li>
 *
 * @Version v1.0
 * @Author Administrator
 * @Date 2021-03-09 -- 15:56
 * @Description TODO
 *
 * 具体的结构图请见mindmaster文档描述
 *
 */
public class CallableDemo7 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallAble());

        new Thread(futureTask,"A").start();
        new Thread(futureTask,"B").start();   //即使有两个线程进行调用，第二个线程会进行结果复用

        //等待主线程结束在合并futureTask线程结果
        System.out.println(futureTask.get());  //get()方法会阻塞，等待MyCallAble线程执行完成后获得结果才会继续往下执行，非必要时应放在程序最后处获得结果
    }

}

class MyCallAble implements Callable<Integer>{

    public Integer call() throws Exception {
        return 1024;
    }
}
