package yuriy.weiss.iq.puzzler.multithreading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuriy.weiss.iq.puzzler.calc.CalcEngine;
import yuriy.weiss.iq.puzzler.calc.ShapePlacer;
import yuriy.weiss.iq.puzzler.calc.strategy.BoardPreparationStrategy;
import yuriy.weiss.iq.puzzler.model.State;

import java.util.concurrent.TimeUnit;

import static yuriy.weiss.iq.puzzler.multithreading.ShapePlacerMode.CONSUMER;

public class StateConsumer implements Runnable {

    private static final Logger logger = LogManager.getLogger();

    private final CalcEngine calcEngine;
    private final BoardPreparationStrategy boardPreparationStrategy;
    private final int consumerId;

    public StateConsumer( CalcEngine calcEngine, BoardPreparationStrategy boardPreparationStrategy, int consumerId ) {
        this.calcEngine = calcEngine;
        this.boardPreparationStrategy = boardPreparationStrategy;
        this.consumerId = consumerId;
    }

    @Override
    public void run() {
        try {
            State state = calcEngine.getStateQueue().poll( 2, TimeUnit.SECONDS );
            while ( state != null && calcEngine.getSuccessState() == null ) {
                logger.debug( "CONSUMER {} state taken from queue", consumerId );
                State result = new ShapePlacer( calcEngine, state, boardPreparationStrategy, CONSUMER )
                        .tryPlaceNotUsedShapes();
                if ( result != null && result.isPlacementSuccess() ) {
                    calcEngine.setSuccessState( result );
                    calcEngine.onStateSuccess();
                }
                state = calcEngine.getStateQueue().poll( 2, TimeUnit.SECONDS );
            }
        } catch ( InterruptedException e ) {
            Thread.currentThread().interrupt();
        }
        if ( calcEngine.getSuccessState() != null ) {
            logger.info( "CONSUMER {} stops on success", consumerId );
        }
        calcEngine.onConsumerFinish( consumerId );
    }
}
