package cn.sino.share.thread;
/**
 * 多个线程 操作同一个对象
 *
 * */
public class TestIIIIThread implements Runnable{
    // 茅台剩余数量
    private Integer numbers = 10;

    @Override
    public void run() {
        while (true){
            if (numbers <= 0){
                break;
            }
//            try {
//                Thread.sleep(200);
//            }catch (Exception e){}

            System.out.println(Thread.currentThread().getName() + "-- 抢到了第" + numbers-- + "瓶茅台");
        }
    }

    public static void main(String[] args) {
        TestIIIIThread maotai = new TestIIIIThread();

        new Thread(maotai, "小明").start();
        new Thread(maotai, "黄牛").start();
        new Thread(maotai, "tiger").start();


    }
}
