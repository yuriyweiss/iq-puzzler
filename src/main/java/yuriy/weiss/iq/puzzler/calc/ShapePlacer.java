package yuriy.weiss.iq.puzzler.calc;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuriy.weiss.iq.puzzler.PuzzlerException;
import yuriy.weiss.iq.puzzler.calc.strategy.BoardPreparationStrategy;
import yuriy.weiss.iq.puzzler.kpi.KpiHolder;
import yuriy.weiss.iq.puzzler.model.Board;
import yuriy.weiss.iq.puzzler.model.Cell;
import yuriy.weiss.iq.puzzler.model.Shape;
import yuriy.weiss.iq.puzzler.model.ShapeVariant;
import yuriy.weiss.iq.puzzler.model.State;
import yuriy.weiss.iq.puzzler.model.UsedShape;
import yuriy.weiss.iq.puzzler.multithreading.ShapePlacerMode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static yuriy.weiss.iq.puzzler.model.Constants.PRINT_SEPARATOR;
import static yuriy.weiss.iq.puzzler.multithreading.ShapePlacerMode.PRODUCER;

public class ShapePlacer {

    private static final Logger logger = LogManager.getLogger();

    private static final Boolean NEED_BREAK = Boolean.TRUE;

    private final CalcEngine calcEngine;
    private final State state;
    private final BoardPreparationStrategy boardPreparationStrategy;
    private final int producerThreshold;
    private final ShapePlacerMode shapePlacerMode;

    public ShapePlacer( CalcEngine calcEngine, State state, BoardPreparationStrategy boardPreparationStrategy,
            ShapePlacerMode shapePlacerMode ) {
        this.calcEngine = calcEngine;
        this.state = state;
        this.boardPreparationStrategy = boardPreparationStrategy;
        this.producerThreshold = calcEngine.getProducerThreashold();
        this.shapePlacerMode = shapePlacerMode;
    }

    /**
     * If there are several shapes not processed yet, then select one of them.<br>
     * Calculate all possible placements of all such shape variants.<br>
     * Then for each possible placement create new calculation state (board + not used shapes).<br>
     * Then recursive call ShapePlacer.tryPlaceNotUsedShapes() for new state.
     * <p>
     * In most cases placement will return failed result, as only rare placements fill the board correctly.<br>
     * In case of failure method returns null.<br>
     * In case of success method returns state with filled board, and no not used shapes.
     *
     * @return success state or null if placement failed
     */
    public State tryPlaceNotUsedShapes() {
        if ( state.getNotUsedShapes().isEmpty() ) {
            throw new PuzzlerException( "tryPlaceNotUsedShapes must have at least 1 not used shape to process" );
        }

        // success state was already reached, stop execution
        if ( calcEngine.getSuccessState() != null ) {
            logStoppingExecutionOnSuccess();
            return null;
        }

        logProcessingState( state );

        boardPreparationStrategy.prepareBoardBeforePlacement( state.getBoard() );

        Map<ShapeVariant, List<Cell>> possiblePlacements = buildPossiblePlacementsForEachVariant( state );
        logger.debug( "possiblePlacements: {}", possiblePlacements );

        // count sum of all placements for all variants of one shape
        Map<Shape, Integer> placementsCountByShape = possiblePlacements.entrySet().stream()
                .collect( Collectors.groupingBy( entry -> entry.getKey().getShape(),
                        Collectors.summingInt( entry -> entry.getValue().size() ) ) );

        // select shape with minimum placements
        Optional<Map.Entry<Shape, Integer>> minShapeEntry = placementsCountByShape.entrySet().stream()
                .min( Comparator.comparingInt( Map.Entry::getValue ) );
        if ( !minShapeEntry.isPresent() ) {
            return null;
        }
        Shape minShape = minShapeEntry.get().getKey();

        if ( minShapeEntry.get().getValue() == 0 ) {
            // no placement for any variant of shape was found - placement failed
            logFailedPlacement( state );
            return null;
        }

        return checkAllPossiblePlacements( state, minShape, possiblePlacements );
    }

    private Map<ShapeVariant, List<Cell>> buildPossiblePlacementsForEachVariant( State state ) {
        Map<ShapeVariant, List<Cell>> result = new HashMap<>();
        List<Shape> shapes = state.getNotUsedShapes();
        shapes.forEach( shape -> shape.getVariants()
                .forEach( shapeVariant -> buildPossiblePlacements( result, shapeVariant ) ) );
        return result;
    }

    private void buildPossiblePlacements( Map<ShapeVariant, List<Cell>> result, ShapeVariant shapeVariant ) {
        List<Cell> placements = new ArrayList<>();
        Board board = state.getBoard();
        board.getPossibleCells().forEach( cell -> {
            if ( variantCanBePlaced( board, cell.getX(), cell.getY(), shapeVariant ) ) {
                placements.add( new Cell( cell.getX(), cell.getY() ) );
            }
        } );
        if ( !placements.isEmpty() ) {
            result.put( shapeVariant, placements );
        }
    }

    private boolean variantCanBePlaced( Board board, int x, int y, ShapeVariant shapeVariant ) {
        KpiHolder.getVariantCanBePlacedKpi().inc();
        if ( x + shapeVariant.getWidth() > board.getWidth() || y + shapeVariant.getHeight() > board.getHeight() ) {
            return false;
        }
        List<Cell> cellsToCheck = shapeVariant.getFilledCells();
        int[][] boardCells = board.getCells();
        for ( Cell cellToCheck : cellsToCheck ) {
            if ( boardCells[x + cellToCheck.getX()][y + cellToCheck.getY()] == 1 ) {
                return false;
            }
        }
        return true;
    }

    private State checkAllPossiblePlacements( State state, Shape shape,
            Map<ShapeVariant, List<Cell>> possiblePlacements ) {
        for ( Map.Entry<ShapeVariant, List<Cell>> possiblePlacement : possiblePlacements.entrySet() ) {
            ShapeVariant shapeVariant = possiblePlacement.getKey();
            if ( !shapeVariant.getShape().equals( shape ) ) {
                continue;
            }
            List<Cell> variantPlacements = possiblePlacement.getValue();
            for ( Cell variantPlacement : variantPlacements ) {
                Pair<State, Boolean> result = processVariantPlacement( state, shape, shapeVariant, variantPlacement );
                // return from method only if special conditions fired or success state reached
                if ( result.getRight() == NEED_BREAK ) {
                    return result.getLeft();
                }
            }
        }
        return null;
    }

    private Pair<State, Boolean> processVariantPlacement( State state, Shape shape, ShapeVariant shapeVariant,
            Cell variantPlacement ) {
        // success state was already reached, stop execution
        if ( calcEngine.getSuccessState() != null ) {
            logStoppingExecutionOnSuccess();
            return new ImmutablePair<>( null, NEED_BREAK );
        }
        State stateToCheck = prepareStateToCheck( state, shape, shapeVariant, variantPlacement );
        // when success, there will be only one variant placement
        if ( stateToCheck.getNotUsedShapes().isEmpty() ) {
            stateToCheck.setPlacementSuccess( true );
            return new ImmutablePair<>( stateToCheck, NEED_BREAK );
        }
        // when called from producer, puts state to queue for consumers
        if ( shapePlacerMode == PRODUCER && stateToCheck.getNotUsedShapes().size() <= producerThreshold ) {
            putStateToQueue( stateToCheck );
            logger.debug( "{} ShapePlacer state put to queue", shapePlacerMode );
            return new ImmutablePair<>( null, !NEED_BREAK );
        }
        // when called from consumer, performs full execution chain until result
        State result = new ShapePlacer( calcEngine, stateToCheck,
                boardPreparationStrategy, shapePlacerMode ).tryPlaceNotUsedShapes();
        Boolean needBreak = result != null && result.isPlacementSuccess();
        return new ImmutablePair<>( result, needBreak );
    }

    private State prepareStateToCheck( State state, Shape shape, ShapeVariant shapeVariant, Cell variantPlacement ) {
        Board newBoard = new Board( state.getBoard() );
        newBoard.addUsedShape(
                new UsedShape( variantPlacement.getX(), variantPlacement.getY(), shape,
                        shapeVariant.getIndex() ) );
        List<Shape> newNotUsedShapes = new ArrayList<>( state.getNotUsedShapes() );
        newNotUsedShapes.remove( shape );
        return new State( newBoard, newNotUsedShapes );
    }

    private void putStateToQueue( State state ) {
        try {
            calcEngine.getStateQueue().put( state );
            KpiHolder.getStatesProducedKpi().inc();
        } catch ( InterruptedException e ) {
            Thread.currentThread().interrupt();
        }
    }

    private void logProcessingState( State state ) {
        if ( logger.isDebugEnabled() ) {
            logger.debug( PRINT_SEPARATOR );
            logger.debug( "PROCESSING STATE" );
            logger.debug( "[{}] variantCanBePlaced called", KpiHolder.getVariantCanBePlacedKpi().getValue() );
            logger.debug( "[{}] preparation time", KpiHolder.getPreparationTimeKpi().getValue() );
            logger.debug( state.getBoard().print() );
        }
    }

    private void logFailedPlacement( State state ) {
        if ( logger.isDebugEnabled() ) {
            logger.debug( PRINT_SEPARATOR );
            logger.debug( "PLACEMENT FAILED" );
            logger.debug( state.getBoard().print() );
        }
    }

    private void logStoppingExecutionOnSuccess() {
        logger.info( "SUCCESS state reached, stopping execution of {}", shapePlacerMode );
    }
}
