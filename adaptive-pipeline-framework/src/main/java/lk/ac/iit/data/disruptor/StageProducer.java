package lk.ac.iit.data.disruptor;

import com.lmax.disruptor.RingBuffer;
import lk.ac.iit.data.StageEvent;

public class StageProducer {

    private final RingBuffer<StageEvent> ringBuffer;

    public StageProducer(RingBuffer<StageEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(StageEvent stageEvent, long id) {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try {
            StageEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            //event.set(stageEvent.getLong(0), id);  // Fill with data
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
