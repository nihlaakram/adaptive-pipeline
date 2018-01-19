import lk.ac.iit.core.Monitor;

public class MonitorTest {

    public static void main(String[] args) {
        Monitor.initMonitor(2, 1000);

        Monitor.getMonitor();
        Monitor monitor = Monitor.getMonitor();

        monitor.setLatency(new long[]{1,2,3});
    }
}
