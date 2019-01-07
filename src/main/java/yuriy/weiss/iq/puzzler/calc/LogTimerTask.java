package yuriy.weiss.iq.puzzler.calc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuriy.weiss.iq.puzzler.kpi.KpiHolder;

import java.util.TimerTask;

public class LogTimerTask extends TimerTask {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        logger.info( "" );
        logger.info( "[{}] variantCanBePlaced called", KpiHolder.getVariantCanBePlacedKpi().getValue() );
        logger.info( "[{}] preparation time", KpiHolder.getPreparationTimeKpi().getValue() );
        logger.info( "[{}] placement time", KpiHolder.getPlacementTimeKpi().getValue() );
    }
}
