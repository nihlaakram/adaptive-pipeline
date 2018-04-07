package lk.ac.iit.core.monitor;

import lk.ac.iit.core.executor.ScalableContextListener;

public class JPipeMonitor {

    private ScalableContextListener listener;

    public JPipeMonitor(ScalableContextListener listener) {
        this.listener = listener;

    }

    /**
     * Add comment
     * @return
     */
    public ScalableContextListener getListener() {
        return this.listener;
    }
}
