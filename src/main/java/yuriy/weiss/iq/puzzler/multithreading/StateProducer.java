package yuriy.weiss.iq.puzzler.multithreading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuriy.weiss.iq.puzzler.calc.CalcEngine;
import yuriy.weiss.iq.puzzler.calc.ShapePlacer;
import yuriy.weiss.iq.puzzler.calc.strategy.BoardPreparationStrategy;
import yuriy.weiss.iq.puzzler.kpi.KpiHolder;
import yuriy.weiss.iq.puzzler.model.State;

import static yuriy.weiss.iq.puzzler.multithreading.ShapePlacerMode.PRODUCER;

public class StateProducer implements Runnable {

    private static final Logger logger = LogManager.getLogger();

    private final CalcEngine calcEngine;
    private final State initialState;
    private final BoardPreparationStrategy boardPreparationStrategy;

    public StateProducer( CalcEngine calcEngine, State initialState,
            BoardPreparationStrategy boardPreparationStrategy ) {
        this.calcEngine = calcEngine;
        this.initialState = initialState;
        this.boardPreparationStrategy = boardPreparationStrategy;
    }

    @Override
    public void run() {
        // check threshold, if it is greater than notUsedShapes, then place single state to queue
        if ( initialState.getNotUsedShapes().size() <= calcEngine.getProducerThreashold() ) {
            try {
                calcEngine.getStateQueue().put( initialState );
                KpiHolder.getStatesProducedKpi().inc();
                logger.info( "threshold is greater than not used shapes size" );
                logger.info( "state sent directly to consumer" );
            } catch ( InterruptedException e ) {
                Thread.currentThread().interrupt();
            }
        } else {
            new ShapePlacer( calcEngine, initialState, boardPreparationStrategy, PRODUCER ).tryPlaceNotUsedShapes();
        }
        calcEngine.onProducerFinish();
    }
}
