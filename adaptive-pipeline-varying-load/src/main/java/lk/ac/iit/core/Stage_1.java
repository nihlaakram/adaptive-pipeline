package lk.ac.iit.core;

import com.lmax.disruptor.EventHandler;
import lk.ac.iit.data.LongEvent;

public class Stage_1 implements EventHandler<LongEvent> {
    private String name;
    private final int id;
    private static int num;
    private static int count =0;
    private static long starttime;
    private static long totallatency = 0;


    public Stage_1(String name, int id, int num){
        this.name = name;
        this.id = id;
        this.num = num;
        this.count =0;
        this.totallatency =0;
        starttime = System.currentTimeMillis();
    }

    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {

        if(event.getId()%getNum()==this.id){
            //for(int i=0; i<10000; i++){}
            incrementCount();
            totallatency= (System.currentTimeMillis()-event.getTimestamp());

            if(count%100==0){
                System.out.println(count+ "\tavg lat: "+ ((totallatency*1.0)/count));
            }
        }
    }


    public synchronized static void setNum(int num) {
        Stage_1.num = num;
    }

    public synchronized static long getNum() {
        return Stage_1.num;
    }

    private synchronized static void incrementCount(){
        count++;
    }



}
