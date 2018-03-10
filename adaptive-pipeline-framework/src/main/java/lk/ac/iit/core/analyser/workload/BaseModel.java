package lk.ac.iit.core.analyser.workload;

public interface BaseModel {

    /**
     * Returns the number of recommended workers for a give input rate and workload parameter
     *
     * @param inputRate     the rate at which input is pumped in to the worker/workers
     * @param workLoadParam the workload parameter of the worker/workers
     * @return the recommended number of workers
     */
    int getWorkers(int inputRate, int workLoadParam);

    /**
     * Returns the number of recommended workers for a give input rate and workload parameter
     *
     * @param workLoadParam the workload parameter of the worker/workers
     * @return the recommended number of workers
     */
    int getWorkers( int workLoadParam);


    /**
     * Add past experiment results to the model
     *
     * @param workLoadParam the workload parameter of the worker/workers
     * @param workers the number of recommended workers
     *
     */
    void addWorkers(int workLoadParam, int workers);

    /**
     * Set the Workload model
     *
     * @param baseModel the workload model
     */
    void setModel(WorkLoadModel baseModel);

    /**
     * Add past experiment results to the model
     *
     * @param inputRate     the rate at which input is pumped in to the worker/workers
     * @param workLoadParam the workload parameter of the worker/workers
     * @param workers the number of recommended workers
     *
     */
    void addWorkers(int inputRate, int workLoadParam, int workers);


}
