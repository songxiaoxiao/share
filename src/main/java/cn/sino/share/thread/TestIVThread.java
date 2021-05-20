package cn.sino.share.thread;

import java.util.concurrent.*;

/**
 * 多个线程 操作同一个对象
 *
 * */
public class TestIVThread implements Callable<Boolean> {
    // 茅台剩余数量
    private Integer numbers = 10;

    private String name;
    public TestIVThread(String name){
        this.name = name;
    }
    @Override
    public Boolean call() throws Exception {
        System.out.println(name);
        return true;
    }
133
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TestIVThread thread1 = new TestIVThread("winter");
        TestIVThread thread2 = new TestIVThread("song");
        TestIVThread thread3 = new TestIVThread("xiao");

        // 执行器
        ExecutorService ser = Executors.newFixedThreadPool(3);

        // 提交执行
        Future<Boolean> r1 = ser.submit(thread1);
        Future<Boolean> r2 = ser.submit(thread2);
        Future<Boolean> r3 = ser.submit(thread3);


        // 获取结果
        Boolean aBoolean = r1.get();
        Boolean aBoolean1 = r2.get();
        Boolean aBoolean2 = r3.get();

        // 关闭服务
        ser.shutdown();
    }
}
