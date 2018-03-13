package lk.ac.iit.usecase.usecase01;

import lk.ac.iit.data.PipeData;
import lk.ac.iit.handler.PerformanceHandler;
import lk.ac.iit.handler.ProductionHandler;
import lk.ac.iit.handler.ScalableContextListner;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;

import java.util.concurrent.LinkedBlockingQueue;

public class NeeMain {

    public static void main(String[] args) {


        BasicConfigurator.configure(new ConsoleAppender(new PatternLayout(" [%t] %p - %m%n")));
        //the message size
        int messageSize = 100000;

        //the no. of messages to use
        int messageCount = 10000;

        //no of stages
        int stageCount = 2;

        //contribution from each stage to the string
        int charCount = messageSize / stageCount;


        //required queues
        LinkedBlockingQueue<PipeData> in = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<PipeData> out = new LinkedBlockingQueue<>();

        //create the stages
        ScalableContextListner listner = new ScalableContextListner();
        listner.contextInitialized(in, out, stageCount);


        PerformanceHandler term = new PerformanceHandler(out, System.currentTimeMillis());
        Thread t1 = new Thread(term, "termination");
        t1.start();

        //fill data
        ProductionHandler prod = new ProductionHandler(in, out, messageCount, messageSize, stageCount, listner);
        Thread t2 = new Thread(prod);
        t2.start();

    }
}


/**
 * public class NeeMain {
 * <p>
 * public static void main(String[] args) {
 * <p>
 * //the message size
 * int messageSize = 10;
 * <p>
 * //the no. of messages to use
 * int messageCount = 10000;
 * <p>
 * //no of stages
 * int stageCount = 2;
 * <p>
 * //contribution from each stage to the string
 * int charCount = messageSize/stageCount;
 * <p>
 * <p>
 * <p>
 * //required queues
 * LinkedBlockingQueue<XMLMessage>[] queues = new LinkedBlockingQueue[stageCount+1];
 * for(int i=0; i<stageCount+1; i++){
 * queues[i] = new LinkedBlockingQueue<XMLMessage>();
 * }
 * <p>
 * //create the stages
 * Stage [] stages = new Stage[stageCount];
 * for(int i=0; i<stageCount; i++){
 * stages[i] = new Stage(queues[i], queues[i+1]);
 * }
 * <p>
 * //start threads
 * Thread[] threads = new Thread[stageCount];
 * for(int i=0; i<stageCount; i++){
 * threads[i] = new Thread(stages[i]);
 * threads[i].start();
 * }
 * <p>
 * <p>
 * <p>
 * PerformanceHandler term = new PerformanceHandler(queues[stageCount], System.currentTimeMillis());
 * Thread t1 = new Thread(term);
 * t1.start();
 * <p>
 * //fill data
 * Producer prod = new Producer(queues[0], messageCount, messageSize, stageCount);
 * Thread t2 = new Thread(prod);
 * t2.start();
 * <p>
 * }
 * }
 * <p>
 * class Stage implements Runnable {
 * LinkedBlockingQueue<XMLMessage> queue;
 * LinkedBlockingQueue<XMLMessage> queue1;
 * <p>
 * <p>
 * public Stage(LinkedBlockingQueue<XMLMessage> queue, LinkedBlockingQueue<XMLMessage> queue1) {
 * this.queue = queue;
 * this.queue1 = queue1;
 * <p>
 * }
 *
 * @Override public void run() {
 * while (true) {
 * try {
 * XMLMessage msg = this.queue.take();
 * if(msg.getTimestamp()==-1){
 * this.queue1.put(msg);
 * <p>
 * //terminate
 * } else if(msg.getTimestamp()==-2){
 * this.queue1.put(msg);
 * break;
 * //terminate
 * } else {
 * RandomStringGenerator random = new RandomStringGenerator.Builder()
 * .withinRange('0', 'z').build();
 * String charList = random.generate(msg.getWorkload());
 * msg.addToMessage(charList);
 * this.queue1.put(msg);
 * }
 * } catch (InterruptedException e) {
 * e.printStackTrace();
 * }
 * }
 * <p>
 * }
 * }
 * <p>
 * class Producer implements Runnable {
 * <p>
 * LinkedBlockingQueue<XMLMessage> inQueue;
 * int messageCount;
 * int workload;
 * int workers;
 * <p>
 * public Producer(LinkedBlockingQueue<XMLMessage> inQueue, int messageCount, int workload, int workers) {
 * this.inQueue = inQueue;
 * this.messageCount = messageCount;
 * this.workload = workload;
 * this.workers =workers;
 * }
 * @Override public void run() {
 * <p>
 * try {
 * for(int i =0; i<messageCount; i++){
 * DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
 * DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 * <p>
 * <p>
 * Document doc = docBuilder.newDocument();
 * Element rootElement = doc.createElement("XML_MESSAGE");
 * doc.appendChild(rootElement);
 * <p>
 * this.inQueue.put(new XMLMessage(System.currentTimeMillis(), doc, rootElement, this.workload/this.workers));
 * <p>
 * }
 * this.inQueue.put(new XMLMessage(-1, null, null, this.workload));
 * <p>
 * for(int i =0; i<messageCount; i++){
 * DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
 * DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 * <p>
 * <p>
 * Document doc = docBuilder.newDocument();
 * Element rootElement = doc.createElement("XML_MESSAGE");
 * doc.appendChild(rootElement);
 * <p>
 * this.inQueue.put(new XMLMessage(System.currentTimeMillis(), doc, rootElement, 100000/this.workers));
 * <p>
 * }
 * this.inQueue.put(new XMLMessage(-2, null, null, this.workload));
 * } catch (InterruptedException e) {
 * e.printStackTrace();
 * } catch (ParserConfigurationException e) {
 * e.printStackTrace();
 * }
 * }
 * }
 */