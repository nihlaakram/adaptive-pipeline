import lk.ac.iit.core.planner.Planner;
import lk.ac.iit.core.analyser.AnalyserData;

public class PlannerTest {

    public static void main(String[] args) {
        AnalyserData data = new AnalyserData(new double[]{1000.0, 250.0}, new double[]{1.0, 5.0});

        Planner planner = new Planner(data);
        System.out.println(planner.getNoOfThread());

        System.out.println(planner.getMAX_THREADS());

    }
}
