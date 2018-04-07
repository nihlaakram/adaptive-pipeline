package lk.ac.iit.core.analyser.data;

public class AnalysedData {

    private int handlerID;
    private boolean scalability;

    public AnalysedData(int handlerID, boolean scalability) {
        this.handlerID = handlerID;
        this.scalability = scalability;
    }

    public int getHandlerID() {
        return handlerID;
    }

    public boolean isScalability() {
        return scalability;
    }


}
