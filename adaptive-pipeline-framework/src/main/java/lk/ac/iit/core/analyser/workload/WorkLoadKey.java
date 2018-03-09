package lk.ac.iit.core.analyser.workload;

public class WorkLoadKey {

    private int inputRate;
    private int workerParam;

    public  WorkLoadKey(int inputRate, int workerParam){
        this.inputRate = inputRate;
        this.workerParam = workerParam;
    }

    public boolean equals(Object obj) {

        if (obj instanceof WorkLoadKey) {

            WorkLoadKey key = (WorkLoadKey) obj;

            return ((this.inputRate==key.inputRate) && (this.workerParam==key.workerParam));

        }

        return false;

    }



    public int hashCode() {
        return (31 * this.inputRate) ^ this.workerParam;
    }
}
