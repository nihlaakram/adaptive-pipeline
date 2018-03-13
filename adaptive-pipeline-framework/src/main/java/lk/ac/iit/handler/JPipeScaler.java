package lk.ac.iit.handler;

import lk.ac.iit.core.analyser.workload.WorkLoadModel;
import lk.ac.iit.data.Data_Single;
import lk.ac.iit.data.queue.AdaptiveInputQueue;
import lk.ac.iit.data.queue.AdaptiveOutputQueue;

import java.util.concurrent.BlockingQueue;

public class JPipeScaler {

    private final BlockingQueue<Data_Single> inputQueue;
    private final BlockingQueue<Data_Single> outputQueue;
    private WorkLoadModel workLoadModel;
    private boolean scale;

    public JPipeScaler(WorkLoadModel workLoadModel, boolean scale) {
        this.workLoadModel = workLoadModel;
        this.inputQueue = AdaptiveInputQueue.getInputQueue();
        this.outputQueue = AdaptiveOutputQueue.getOutputQueue();
        this.scale = scale;
    }

    public JPipeScaler(WorkLoadModel workLoadModel) {
        this.workLoadModel = workLoadModel;
        this.inputQueue = AdaptiveInputQueue.getInputQueue();
        this.outputQueue = AdaptiveOutputQueue.getOutputQueue();
        this.scale = false;
    }


    public WorkLoadModel getWorkLoadModel() {
        return workLoadModel;
    }

    public void setWorkLoadModel(WorkLoadModel workLoadModel) {
        this.workLoadModel = workLoadModel;
    }

    public BlockingQueue<Data_Single> getInputQueue() {
        return inputQueue;
    }

    public BlockingQueue<Data_Single> getOutputQueue() {
        return outputQueue;
    }

    public boolean isScale() {
        return scale;
    }


}
