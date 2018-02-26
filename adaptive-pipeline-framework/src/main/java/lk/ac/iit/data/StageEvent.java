package lk.ac.iit.data;

public class StageEvent {

    private long[] timestampArr;
    private Object data;
    private long id;


    //    @Override
    public long[] getTimestamp() {
        return this.timestampArr;
    }

    //
////    @Override
    public void setTimestamp(int stageId) {
        this.timestampArr[stageId] = System.currentTimeMillis();
    }

    //    @Override
    public Object getDataObject() {
        return this.data;
    }

    //    @Override
    public void set(long id, Object data) {
        //System.out.println("hello");
        this.id = id;
        this.data = data;
        int noOfStages = 2;
        this.timestampArr = new long[noOfStages + 1];
        if (noOfStages > 0) {
            this.timestampArr[0] = System.currentTimeMillis();
        }

    }

    public void setBack(long id, Object data) {
        //System.out.println("hello");
        this.id = id;
        this.data = data;


    }

//    public StageEvent_(String str){}

    public long getId() {
        return this.id;
    }
}
