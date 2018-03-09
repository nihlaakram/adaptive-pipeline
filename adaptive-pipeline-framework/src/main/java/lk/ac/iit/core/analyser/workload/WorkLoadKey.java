package lk.ac.iit.core.analyser.workload;

public class WorkLoadKey {

    private int inputRate;
    private int workLoadParam;

    /** The key to access the WorkLoadModel
     *
     * @param inputRate the rate at which input is pumped in to the worker/workers
     * @param workLoadParam the workload parameter of the worker/workers
     */
    public WorkLoadKey(int inputRate, int workLoadParam) {
        this.inputRate = inputRate;
        this.workLoadParam = workLoadParam;
    }

    public boolean equals(Object obj) {
        if (obj instanceof WorkLoadKey) {
            WorkLoadKey key = (WorkLoadKey) obj;
            return ((this.inputRate == key.inputRate) && (this.workLoadParam == key.workLoadParam));
        }
        return false;

    }


    public int hashCode() {
        return (31 * this.inputRate) ^ this.workLoadParam;
    }
}
