package cn.sino.share.thread;

// 实现 runnable , 实现接口。重写run。调用start
public class TestIIIThread implements Runnable {
    @Override
    public void run() {
        for(int i = 0; i < 20; i++){
            System.out.println("我看代码----" + i);
        }
    }
    // 简单事例 交替执行
    public static void main(String[] args) {
        TestIIIThread testThread = new TestIIIThread();

        // 通过thread 代理
        Thread thread = new Thread(testThread);
        thread.start();

        for (int i = 0; i < 2000; i++){
            System.out.println("我在学习多线程" + i);
        }
    }
}
