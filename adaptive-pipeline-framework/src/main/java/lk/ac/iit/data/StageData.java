package lk.ac.iit.data;

public class StageData implements Data {

    private long[] timestampArr;
    private Object data;

    public StageData(int noOfStages, Object data) {
        this.timestampArr = new long[noOfStages + 1];
        if (noOfStages > 0) {
            this.timestampArr[0] = System.currentTimeMillis();
        }

        this.data = data;
    }

    @Override
    public long[] getTimestamp() {
        return this.timestampArr;
    }

    @Override
    public void setTimestamp(int stageId) {
        this.timestampArr[stageId] = System.currentTimeMillis();
    }

    @Override
    public Object getDataObject() {
        return this.data;
    }

    @Override
    public void setDataObject(Object dataObject) {
        this.data = dataObject;
    }
}
