import lk.ac.iit.core.Monitor;

public class MonitorTest {

    public static void main(String[] args) {
        Monitor.initMonitor(2, 1000);

        //Monitor.getMonitor();
        Monitor monitor = Monitor.getMonitor();
        Thread t1 = new Thread(monitor);
        t1.start();
        for (int i =0; i<1000; i++){
            monitor.setLatency(new long[]{i,i+1});
        }
        System.out.println("completed sending data");

    }
}
