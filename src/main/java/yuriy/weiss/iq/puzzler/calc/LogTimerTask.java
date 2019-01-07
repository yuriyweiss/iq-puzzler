package yuriy.weiss.iq.puzzler.calc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuriy.weiss.iq.puzzler.kpi.KpiHolder;

import java.util.TimerTask;

public class LogTimerTask extends TimerTask {

    private static final Logger logger = LogManager.getLogger();

    private final CalcEngine calcEngine;

    public LogTimerTask(CalcEngine calcEngine) {
        this.calcEngine = calcEngine;
    }

    @Override
    public void run() {
        logger.info("");
        logger.info("[{}] variantCanBePlaced called", KpiHolder.getVariantCanBePlacedKpi().getValue());
        logger.info("[{}] states PRODUCED", KpiHolder.getStatesProducedKpi().getValue());
        logger.info("[{}] states CONSUMED", KpiHolder.getStatesConsumedKpi().getValue());
        logger.info("[{}] queue size", calcEngine.getStateQueue().size());
        logger.info("[{}] consumer AVG processing time", KpiHolder.getConsumerStateAvgTimeKpi().getValue());
        //logger.info("[{}] preparation time", KpiHolder.getPreparationTimeKpi().getValue());
    }
}
