package lk.ac.iit.usecase.usecase01;

import lk.ac.iit.core.analyser.workload.WorkLoadModel;

class IndexProcessor implements Runnable {

    private volatile boolean running = true;

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                System.out.println("Sleeping...");
                Thread.sleep((long) 15000);

                System.out.println("Processing");
            } catch (InterruptedException e) {
                System.out.println("Exception" + e);
                running = false;
            }
        }

    }
}

class SearchEngineContextListener {


    private Thread thread[] = new Thread[5];
    private IndexProcessor runnable[] = new IndexProcessor[5];


    public void contextInitialized() {
        for (int i = 0; i < 5; i++) {
            runnable[i] = new IndexProcessor();
            thread[i] = new Thread(runnable[i]);
            System.out.println("Starting thread: " + thread[i]);
            thread[i].start();
        }

        System.out.println("Background process successfully started.");
    }


    public void contextDestroyed() {

        for (int i = 0; i < 5; i++) {
            System.out.println("Stopping thread: " + thread[i]);
            if (thread != null) {
                runnable[i].terminate();
                try {
                    thread[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread successfully stopped.");
            }
        }

    }
}

public class Main {
    public static void main(String[] args) {

        // WorkLoadModel model = populateModel();

        // System.out.println(model.getWorkers(1, 10000000));
        System.out.println(Thread.activeCount());
        SearchEngineContextListener se = new SearchEngineContextListener();
        System.out.println(Thread.activeCount());
        se.contextInitialized();
        se.contextDestroyed();


    }

    public static WorkLoadModel populateModel() {
        WorkLoadModel model = new WorkLoadModel();
        model.addWorkers(1, 10, 1);//10b
        model.addWorkers(1, 100, 1);//100b
        model.addWorkers(1, 1000, 1);//1kb
        model.addWorkers(1, 10000, 5);//10kb
        model.addWorkers(1, 100000, 5);//100kb
        model.addWorkers(1, 1000000, 5);//1mb
        model.addWorkers(1, 10000000, 5);//0mb
        return model;
    }
}

// class IndexProcessor implements Runnable {
//
//    private volatile boolean running = true;
//
//    public void terminate() {
//        running = false;
//    }
//
//    @Override
//    public void run() {
//        while (running) {
//            String help = "please";
//            try {
//                System.out.println("Sleeping...");
//                Thread.sleep((long) 15000);
//                help = "please do";
//                System.out.println("Processing");
//            } catch (InterruptedException e) {
//                System.out.println("Exception"+ e);
//                running = false;
//            }
//            System.out.println(help);
//        }
//
//    }
//}
//
// class SearchEngineContextListener {
//
//
//    private Thread thread = null;
//    private IndexProcessor runnable = null;
//
//
//    public void contextInitialized() {
//        runnable = new IndexProcessor();
//        thread = new Thread(runnable);
//        System.out.println("Starting thread: " + thread);
//        thread.start();
//        System.out.println("Background process successfully started.");
//    }
//
//
//    public void contextDestroyed() {
//        System.out.println("Stopping thread: " + thread);
//        if (thread != null) {
//            runnable.terminate();
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Thread successfully stopped.");
//        }
//    }
//}
//
//public class Main {
//    public static void main(String[] args) {
//
//       // WorkLoadModel model = populateModel();
//
//       // System.out.println(model.getWorkers(1, 10000000));
//        System.out.println(Thread.activeCount());
//        SearchEngineContextListener se = new SearchEngineContextListener();
//        System.out.println(Thread.activeCount());
//        se.contextInitialized();
//        try {
//            System.out.println("Sleeping...1");
//            Thread.sleep((long) 1000);
//
//            System.out.println("Processing1");
//        } catch (InterruptedException e) {
//            System.out.println("Exception1"+ e);
//
//        }
//        System.out.println(Thread.activeCount());
//        se.contextDestroyed();
//
//
//
//    }
//
//    public static WorkLoadModel populateModel(){
//        WorkLoadModel model = new WorkLoadModel();
//        model.addWorkers(1, 10, 1);//10b
//        model.addWorkers(1, 100, 1);//100b
//        model.addWorkers(1, 1000, 1);//1kb
//        model.addWorkers(1, 10000, 5);//10kb
//        model.addWorkers(1, 100000, 5);//100kb
//        model.addWorkers(1, 1000000, 5);//1mb
//        model.addWorkers(1, 10000000, 5);//0mb
//        return model;
//    }
//}
