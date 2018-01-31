package lk.ac.iit.execption;

public class NegativeTimestampException extends Exception {

    public NegativeTimestampException(long time) {
        super(String.valueOf(time));
    }
}
