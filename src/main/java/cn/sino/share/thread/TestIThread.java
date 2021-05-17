package cn.sino.share.thread;

//1。 继承Thread 类。重写run方法。
public class TestIThread extends Thread {

    @Override
    public void run() {
        for(int i = 0; i < 20; i++){
            System.out.println("我看代码----" + i);
        }
    }
    // 简单事例 交替执行
    public static void main(String[] args) {
        TestIThread testThread = new TestIThread();
        testThread.start();
        for (int i = 0; i < 2000; i++){
            System.out.println("我在学习多线程" + i);
        }
    }


}
