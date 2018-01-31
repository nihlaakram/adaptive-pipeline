import lk.ac.iit.core.Monitor;
import lk.ac.iit.execption.NegativeTimestampException;

public class MonitorTest {

    public static void main(String[] args) {
        Monitor.initMonitor(null, 3, 1000);

        //Monitor.getMonitor();
        Monitor monitor = Monitor.getMonitor();
        Thread t1 = new Thread(monitor);
        t1.start();
        for (int i =0; i<1000; i++){
            try {
                monitor.setTimestamp(new long[]{i,i+1,i+2});
            } catch (NegativeTimestampException e) {
                e.printStackTrace();
            }

        }
        System.out.println("completed sending data");

    }
}
