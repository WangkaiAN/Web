package test;

public class InterruptThread {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //正常中断的方式
//                try {
//                    while (!Thread.currentThread().isInterrupted()){
//                        System.out.println("转账...");
//                        Thread.sleep(100000);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                //不能正常中断
                while (!Thread.currentThread().isInterrupted()){
                    System.out.println("转账...");
                    try {
                        Thread.sleep(100000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        //t线程中断标志位=false
        Thread.sleep(3000);
        t.interrupt();//t线程中断标志位=true
    }
}
