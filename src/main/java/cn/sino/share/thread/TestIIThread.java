package cn.sino.share.thread;

public class TestIIThread extends Thread{
    private String name;
    public TestIIThread(String name){
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println(name);
    }

    // 巩固, star后 并非立马执行，而是由cpu调度安排。
    public static void main(String[] args) {
        TestIIThread thread1 = new TestIIThread("winter");
        TestIIThread thread2 = new TestIIThread("song");
        TestIIThread thread3 = new TestIIThread("xiao");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
