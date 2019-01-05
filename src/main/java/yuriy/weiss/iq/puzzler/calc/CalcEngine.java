package yuriy.weiss.iq.puzzler.calc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuriy.weiss.iq.puzzler.calc.strategy.BoardPreparationStrategy;
import yuriy.weiss.iq.puzzler.calc.strategy.CheckAreasStrategy;
import yuriy.weiss.iq.puzzler.kpi.KpiHolder;
import yuriy.weiss.iq.puzzler.model.Board;
import yuriy.weiss.iq.puzzler.model.Shape;
import yuriy.weiss.iq.puzzler.model.ShapesRegistry;
import yuriy.weiss.iq.puzzler.model.State;
import yuriy.weiss.iq.puzzler.model.UsedShape;

import java.util.ArrayList;
import java.util.List;

import static yuriy.weiss.iq.puzzler.model.Constants.PRINT_SEPARATOR;

public class CalcEngine {

    private static final Logger logger = LogManager.getLogger();

    private Board board;
    private List<Shape> notUsedShapes = new ArrayList<>();
    private final BoardPreparationStrategy boardPreparationStrategy;

    public CalcEngine(BoardPreparationStrategy boardPreparationStrategy) {
        board = new Board( 11, 5 );
        notUsedShapes.addAll( ShapesRegistry.getShapes() );
        this.boardPreparationStrategy = boardPreparationStrategy;
    }

    public void testPrintBoard() {
        Board testBoard = new Board( 11, 5 );
        testBoard.addUsedShape( new UsedShape( 0, 0, ShapesRegistry.getShape( 0 ), 0 ) );
        testBoard.addUsedShape( new UsedShape( 0, 3, ShapesRegistry.getShape( 9 ), 0 ) );
        testBoard.addUsedShape( new UsedShape( 2, 1, ShapesRegistry.getShape( 10 ), 4 ) );
        logger.info( PRINT_SEPARATOR );
        logger.info( "BOARD" );
        logger.info( testBoard.print() );
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

    public void placeNotUsedShapes() {
        State result = new ShapePlacer( new State( board, notUsedShapes ), boardPreparationStrategy )
                .tryPlaceNotUsedShapes();
        if ( result != null && result.isPlacementSuccess() ) {
            logger.info( PRINT_SEPARATOR );
            logger.info( "PLACEMENT SUCCESS" );
            logger.info( "[{}] variantCanBePlaced called", KpiHolder.getVariantCanBePlacedKpi().getValue() );
            logger.info( "[{}] preparation time", KpiHolder.getPreparationTimeKpi().getValue() );
            logger.info( "[{}] placement time", KpiHolder.getPlacementTimeKpi().getValue() );
            logger.info( result.getBoard().print() );
        } else {
            logger.info( PRINT_SEPARATOR );
            logger.info( "PLACEMENT FAILED" );
        }
    }
}
