package lk.ac.iit.data;

public class StageData {

    private long timestamp;
    private Object dataObject;

    public StageData( Object dataObject){
        this.timestamp = System.currentTimeMillis();
        this.dataObject = dataObject;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public Object getDataObject() {
        return this.dataObject;
    }

    public void setDataObject(Object dataObject) {
        this.dataObject = dataObject;
    }




}
