package lk.ac.iit.core.analyser.workload;

public interface BaseModel {

    /**
     * Returns the number of recommended workers for a give input rate and workload parameter
     *
     * @param inputRate     the rate at which input is pumped in to the worker/workers
     * @param workLoadParam the workload parameter of the worker/workers
     * @return
     */
    public double getWorkers(double inputRate, double workLoadParam);

    /**
     * Set the Workload model
     *
     * @param baseModel the workload model
     */
    public void setModel(WorkloadModel baseModel);
}
