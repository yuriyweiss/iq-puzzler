package yuriy.weiss.iq.puzzler.kpi;

import java.util.concurrent.atomic.AtomicLong;

public class CountKpi {
    private AtomicLong total = new AtomicLong( 0L );

    public void inc() {
        total.getAndIncrement();
    }

    public long getValue() {
        return total.get();
    }
}
