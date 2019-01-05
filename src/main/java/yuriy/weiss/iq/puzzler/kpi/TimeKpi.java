package yuriy.weiss.iq.puzzler.kpi;

import java.util.concurrent.atomic.AtomicLong;

public class TimeKpi {
    private AtomicLong total = new AtomicLong( 0L );

    public void inc( long interval ) {
        total.getAndAdd( interval );
    }

    public long getValue() {
        return total.get();
    }
}
