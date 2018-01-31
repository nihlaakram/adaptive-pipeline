import lk.ac.iit.core.Monitor;

public class MonitorTest {

    public static void main(String[] args) {
        Monitor.initMonitor(null, 3, 1000);

        //Monitor.getMonitor1();
        Monitor monitor1 = Monitor.getMonitor1();
        Thread t1 = new Thread(monitor1);
        t1.start();
        for (int i = 0; i < 2000; i++) {

            monitor1.setTimestamp(new long[]{0,  + 1,  + 2});


        }
        System.out.println("completed sending data");

    }
}
