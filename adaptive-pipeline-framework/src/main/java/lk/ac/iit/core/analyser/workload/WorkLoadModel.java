package lk.ac.iit.core.analyser.workload;

import java.util.HashMap;
import java.util.Map;

public class WorkLoadModel implements BaseModel {

    private Map<WorkLoadKey, Integer> pastResults;

    /**
     * Creates a WorkLoadModel with past data
     */
    public WorkLoadModel() {
        this.pastResults = new HashMap<>();
    }

    /**
     * Returns the resuls of the Workload model
     *
     * @return past results of the model
     */
    public Map<WorkLoadKey, Integer> getPastResults() {
        return this.pastResults;
    }

    /**
     * See BaseModel#getWorkers
     */
    @Override
    public int getWorkers(int inputRate, int workLoadParam) {
        return this.pastResults.get(new WorkLoadKey(inputRate, workLoadParam));
    }

    /**
     * See BaseModel#getWorkers
     */
    @Override
    public int getWorkers(int workLoadParam) {
        return this.pastResults.get(new WorkLoadKey(1, workLoadParam));
    }

    /**
     * See BaseModel#setModel
     */
    @Override
    public void setModel(WorkLoadModel baseModel) {
        this.pastResults = baseModel.getPastResults();
    }

    /**
     * See BaseModel#addWorkers
     */
    @Override
    public void addWorkers(int workLoadParam, int workers) {
        this.pastResults.put(new WorkLoadKey(1, workLoadParam), workers);
    }

    /**
     * See BaseModel#addWorkers
     */
    @Override
    public void addWorkers(int inputRate, int workLoadParam, int workers) {
        this.pastResults.put(new WorkLoadKey(inputRate, workLoadParam), workers);
    }
}
