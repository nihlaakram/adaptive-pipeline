package lk.ac.iit.data;

public class LongEvent {


    private long value;
    private long id;
    private long timestamp;

    public long getValue() {
        return this.value;
    }

    public long getId() {
        return this.id;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void set(long value, long id) {
        this.value = value;
        this.id = id;
        this.timestamp = System.currentTimeMillis();
    }
}
