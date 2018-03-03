package lk.ac.iit.core;

import com.lmax.disruptor.RingBuffer;
import lk.ac.iit.data.LongEvent;

import java.nio.ByteBuffer;

public class Producer {

    private final RingBuffer<LongEvent> ringBuffer;

    public Producer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb, long id) {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try {
            LongEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            event.set(bb.getLong(0), id);  // Fill with data
        } finally {
            ringBuffer.publish(sequence);
        }
    }

}
