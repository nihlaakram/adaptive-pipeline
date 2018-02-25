package lk.ac.iit.data;

public class StageEvent implements Data {

    private long[] timestampArr;
    private Object data;
    private long id;


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
    public void setDataObject(long id, int noOfStages, Object data) {
        this.id = id;
        this.data = data;
        this.timestampArr = new long[noOfStages + 1];
        if (noOfStages > 0) {
            this.timestampArr[0] = System.currentTimeMillis();
        }

    }

    public long getId() {
        return this.id;
    }
}
