package lk.ac.iit.core.analyser.workload;

public class WorkloadModel implements BaseModel{

    private double[][] pastResults;

    /**
     * Creates a WorkLoadModel with past data
     *
     * @param noOfResults the no of workload results that will be added to the model
     */
    public WorkloadModel(int noOfResults) {
        this.pastResults = new double[3][noOfResults];
    }

    /** Returns the resuls of the Workload model
     *
     * @return past results of the model
     */
    public double[][] getPastResults() {
        return this.pastResults;
    }

    @Override
    public double getWorkers(double inputRate, double workLoadParam) {
        return 0;
    }

    @Override
    public void setModel(WorkloadModel baseModel) {
        this.pastResults = baseModel.getPastResults();
    }
}
