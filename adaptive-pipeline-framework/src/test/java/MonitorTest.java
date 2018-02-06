import lk.ac.iit.core.Monitor;

public class MonitorTest {

    public static void main(String[] args) {
        Monitor.initMonitor(2, 1000);

        //Monitor.getMonitor1();
        Monitor monitor1 = Monitor.getMonitor1();

        //monitor1.start();
        for (int i = 0; i < 1000; i++) {

            monitor1.setTimestamp(new long[]{0,  + 1,  + 5});


        }

        for (int i = 0; i < 1000; i++) {

            monitor1.setTimestamp(new long[]{2,  + 4,  + 5});


        }

        monitor1.setTimestamp(new long[]{2,  + 4,  + 5});
        System.out.println("completed sending data");

    }
}
