package lk.ac.iit.data;

public interface Data {

    public long[] getTimestamp();

    public void setTimestamp(int stageId);

    public Object getDataObject();

    public void setDataObject(long id, int noOfStages, Object data);


}
