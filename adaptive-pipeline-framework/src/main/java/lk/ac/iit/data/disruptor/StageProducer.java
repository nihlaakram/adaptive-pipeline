package lk.ac.iit.data.disruptor;

import com.lmax.disruptor.RingBuffer;
import lk.ac.iit.data.StageEvent;

public class StageProducer {

    private final RingBuffer<StageEvent> ringBuffer;
    private int noOfStages;

    public StageProducer(RingBuffer<StageEvent> ringBuffer,  int noOfStages) {
        this.ringBuffer = ringBuffer;
        this.noOfStages = noOfStages;
    }

    public void onData(long id, StageEvent stageEvent) {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try {
            StageEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            event.setDataObject(id, noOfStages, stageEvent);  // Fill with data
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
