package yuriy.weiss.iq.puzzler.kpi;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by anna on 07.01.2019.
 */
public class AverageTimeKpi {
    private AtomicLong totalTime = new AtomicLong( 0L );
    private AtomicLong totalCount = new AtomicLong( 0L );

    public void inc( long time ) {
        totalTime.getAndAdd( time );
        totalCount.getAndIncrement();
    }

    public double getValue() {
        return ( ( double ) totalTime.get() ) / totalCount.get();
    }
}
