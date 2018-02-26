package lk.ac.iit.data.disruptor.handler;

import com.lmax.disruptor.RingBuffer;
import lk.ac.iit.data.StageEvent;


public class InitialStageHandler {


    private final RingBuffer<StageEvent> ringBuffer;

    public InitialStageHandler(RingBuffer<StageEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(Object object, long id) {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try {
            StageEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor

            event.set(id, object);  // Fill with data
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
