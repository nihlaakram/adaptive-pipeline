package lk.ac.iit.data.disruptor;

import com.lmax.disruptor.EventFactory;
import lk.ac.iit.data.StageEvent;

public class StageEventFactory implements EventFactory<StageEvent> {
    public StageEvent newInstance() {
        return new StageEvent();
    }

}
