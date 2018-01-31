package lk.ac.iit.core;

public class Executor implements Runnable {

    private static Executor executor;
    private Planner planner;
    private boolean terminate;

    private Executor() {
        this.planner = new Planner();
        this.terminate = false;
    }

    public synchronized static Executor getExecutor() {
        if (executor == null) {
            executor = new Executor();
        }
        return executor;
    }

    @Override
    public void run() {
        //runs until application terminates
        while (!this.terminate) {

            //plan scaling

            //execute scaling

        }
    }

    public void setTerminate() {
        this.terminate = true;
    }
}
