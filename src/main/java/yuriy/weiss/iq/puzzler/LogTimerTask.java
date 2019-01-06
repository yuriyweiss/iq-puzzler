package yuriy.weiss.iq.puzzler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuriy.weiss.iq.puzzler.kpi.KpiHolder;

import java.util.Date;
import java.util.TimerTask;

public class LogTimerTask extends TimerTask {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        Date logDate = new Date();
        logger.info( "" );
        logger.info( "{} [{}] variantCanBePlaced called", logDate, KpiHolder.getVariantCanBePlacedKpi().getValue() );
        logger.info( "{} [{}] preparation time", logDate, KpiHolder.getPreparationTimeKpi().getValue() );
        logger.info( "{} [{}] placement time", logDate, KpiHolder.getPlacementTimeKpi().getValue() );
    }
}
