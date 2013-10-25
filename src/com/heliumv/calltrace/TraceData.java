package com.heliumv.calltrace;

/**
 * Created with IntelliJ IDEA.
 * User: gp
 * Date: 24.10.13
 * Time: 22:20
 * To change this template use File | Settings | File Templates.
 */
public class TraceData {
    private long firstTimestamp ;
    private long timestamp ;
    private int count ;


    public TraceData()  {
    	clear() ;
    }

    public int incrementCount()  {
        timestamp = System.currentTimeMillis() ;
        return ++count ;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long lastTimestamp) {
        this.timestamp = lastTimestamp;
    }

    public long getFirstTimestamp() {
        return firstTimestamp;
    }

    public long getTimespan() {
        return Math.abs(timestamp - firstTimestamp) ;
    }
    
    public void clear() {
        timestamp = firstTimestamp = System.currentTimeMillis()  ;
        count = 0 ;   	
    }
}
