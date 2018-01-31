package lk.ac.iit.core.planner;

public class LatencyPlanner {

    private boolean scalability;
    private int stageID;

    public LatencyPlanner(boolean scalability, int stageID) {
        this.scalability = scalability;
        this.stageID = stageID;
    }

    public boolean isScalability() {
        return this.scalability;
    }

    public int getStageID() {
        return this.stageID;
    }
}
