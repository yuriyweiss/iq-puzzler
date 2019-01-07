package yuriy.weiss.iq.puzzler.kpi;

public class KpiHolder {
    private static CountKpi variantCanBePlacedKpi = new CountKpi();
    private static CountKpi statesProducedKpi = new CountKpi();
    private static CountKpi statesConsumedKpi = new CountKpi();
    private static CountKpi unsatisfactoryStatesKpi = new CountKpi();
    private static CountKpi totalStatesKpi = new CountKpi();
    private static TimeKpi preparationTimeKpi = new TimeKpi();
    private static AverageTimeKpi consumerStateAvgTimeKpi = new AverageTimeKpi();

    private KpiHolder() {
    }

    public static CountKpi getVariantCanBePlacedKpi() {
        return variantCanBePlacedKpi;
    }

    public static CountKpi getStatesProducedKpi() {
        return statesProducedKpi;
    }

    public static CountKpi getStatesConsumedKpi() {
        return statesConsumedKpi;
    }

    public static TimeKpi getPreparationTimeKpi() {
        return preparationTimeKpi;
    }

    public static AverageTimeKpi getConsumerStateAvgTimeKpi() {
        return consumerStateAvgTimeKpi;
    }

    public static CountKpi getUnsatisfactoryStatesKpi() {
        return unsatisfactoryStatesKpi;
    }

    public static CountKpi getTotalStatesKpi() {
        return totalStatesKpi;
    }
}
