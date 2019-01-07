package yuriy.weiss.iq.puzzler.calc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuriy.weiss.iq.puzzler.calc.strategy.BoardPreparationStrategy;
import yuriy.weiss.iq.puzzler.kpi.KpiHolder;
import yuriy.weiss.iq.puzzler.model.Board;
import yuriy.weiss.iq.puzzler.model.Shape;
import yuriy.weiss.iq.puzzler.model.ShapesRegistry;
import yuriy.weiss.iq.puzzler.model.State;
import yuriy.weiss.iq.puzzler.model.UsedShape;
import yuriy.weiss.iq.puzzler.multithreading.StateConsumer;
import yuriy.weiss.iq.puzzler.multithreading.StateProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static yuriy.weiss.iq.puzzler.model.Constants.PRINT_SEPARATOR;

public class CalcEngine {

    private static final Logger logger = LogManager.getLogger();

    private Board board;
    private List<Shape> notUsedShapes = new ArrayList<>();
    private final BoardPreparationStrategy boardPreparationStrategy;

    private final int threadPoolSize;
    private final int producerThreashold;

    private ArrayBlockingQueue<State> stateQueue = new ArrayBlockingQueue<>( 1000 );
    private ExecutorService threadPool;
    private Timer logTimer;

    private volatile State successState = null;

    public CalcEngine( BoardPreparationStrategy boardPreparationStrategy, int producerThreashold, int threadPoolSize ) {
        board = new Board( 11, 5 );
        notUsedShapes.addAll( ShapesRegistry.getShapes() );
        this.boardPreparationStrategy = boardPreparationStrategy;
        this.producerThreashold = producerThreashold;
        this.threadPoolSize = threadPoolSize;
        this.threadPool = Executors.newFixedThreadPool( threadPoolSize );
    }

    public void addUsedShape( UsedShape usedShape ) {
        board.addUsedShape( usedShape );
        notUsedShapes.remove( usedShape.getShape() );
    }

    public void printBoard() {
        logger.info( PRINT_SEPARATOR );
        logger.info( "BOARD" );
        logger.info( board.print() );
    }

    public void startPlacementThreads() {
        // create and start producer thread
        StateProducer stateProducer = new StateProducer( this, new State( board, notUsedShapes ),
                boardPreparationStrategy );
        Thread stateProducerThread = new Thread( stateProducer );
        stateProducerThread.start();
        // create consumers pool
        for ( int i = 0; i < threadPoolSize; i++ ) {
            threadPool.execute( new StateConsumer( this, boardPreparationStrategy, i ) );
        }
        // create log timer
        logTimer = new Timer();
        logTimer.scheduleAtFixedRate( new LogTimerTask( this ), 5000L, 5000L );
    }

    public void onStateSuccess() {
        logger.info( PRINT_SEPARATOR );
        logger.info( "PLACEMENT SUCCESS" );
        logger.info( "[{}] variantCanBePlaced called", KpiHolder.getVariantCanBePlacedKpi().getValue() );
        logger.info( "[{}] preparation time", KpiHolder.getPreparationTimeKpi().getValue() );
        logger.info( successState.getBoard().print() );

        threadPool.shutdownNow();
        logTimer.cancel();
    }

    public void setSuccessState( State successState ) {
        this.successState = successState;
    }

    public State getSuccessState() {
        return successState;
    }

    public void onProducerFinish() {
        logger.info( "PRODUCER FINISHED" );
    }

    public ArrayBlockingQueue<State> getStateQueue() {
        return stateQueue;
    }

    public int getProducerThreashold() {
        return producerThreashold;
    }

    public void onConsumerFinish( int consumerId ) {
        logger.info( "CONSUMER FINISHED {}", consumerId );
    }
}
