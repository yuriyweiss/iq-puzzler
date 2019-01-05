package yuriy.weiss.iq.puzzler.kpi;

public class KpiHolder {
    private static CountKpi variantCanBePlacedKpi = new CountKpi();
    private static TimeKpi preparationTimeKpi = new TimeKpi();
    private static TimeKpi placementTimeKpi = new TimeKpi();

    private KpiHolder() {}

    public static CountKpi getVariantCanBePlacedKpi() {
        return variantCanBePlacedKpi;
    }

    public static TimeKpi getPreparationTimeKpi() {
        return preparationTimeKpi;
    }

    public static TimeKpi getPlacementTimeKpi() {
        return placementTimeKpi;
    }
}
