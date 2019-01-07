package yuriy.weiss.iq.puzzler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuriy.weiss.iq.puzzler.calc.CalcEngine;
import yuriy.weiss.iq.puzzler.calc.strategy.CheckAreasStrategy;
import yuriy.weiss.iq.puzzler.model.ShapesRegistry;
import yuriy.weiss.iq.puzzler.model.UsedShape;

public class Application {

    private static final Logger logger = LogManager.getLogger();

    public static void main( String[] args ) {
        try {
            if ( args.length == 1 && "printShapes".equals( args[0] ) ) {
                ShapesRegistry.printShapes();
            } else if ( args.length == 2 && "solvePuzzle".equals( args[0] ) ) {
                String puzzleId = args[1];
                solvePuzzle( puzzleId );
            } else {
                printUsage();
            }
        } catch ( Exception e ) {
            logger.error( "Execution error", e );
            printUsage();
        }
    }

    private static void printUsage() {
        String message = "Usage:\n"
                + "printShapes - print available shapes and their variants to build puzzle configuration\n"
                + "solvePuzzle <puzzleId> - run solving algorithm";
        logger.info( message );
    }

    private static void solvePuzzle( String puzzleId ) {
        logger.info( "starting" );

        CalcEngine calcEngine = new CalcEngine( new CheckAreasStrategy(), 6, 6 );
        // CalcEngine calcEngine = new CalcEngine( new WholeBoardStrategy(), 4 )

        addUsedShapesForPuzzle( puzzleId, calcEngine );

        calcEngine.printBoard();

        logger.info( "started" );
        calcEngine.startPlacementThreads();
    }

    private static void addUsedShapesForPuzzle( String puzzleId, CalcEngine calcEngine ) {
        if ( "001".equals( puzzleId ) ) {
            puzzle001( calcEngine );
        } else if ( "018".equals( puzzleId ) ) {
            puzzle018( calcEngine );
        } else if ( "025".equals( puzzleId ) ) {
            puzzle025( calcEngine );
        } else if ( "031".equals( puzzleId ) ) {
            puzzle031( calcEngine );
        } else if ( "037".equals( puzzleId ) ) {
            puzzle037( calcEngine );
        } else {
            throw new PuzzlerException( "puzzle with id [" + puzzleId + "] not registered" );
        }
    }

    private static void puzzle001( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 0, 1, ShapesRegistry.getShape( 9 ), 5 ) );
        calcEngine.addUsedShape( new UsedShape( 0, 0, ShapesRegistry.getShape( 4 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 1, 3, ShapesRegistry.getShape( 8 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 2, 1, ShapesRegistry.getShape( 3 ), 1 ) );
        calcEngine.addUsedShape( new UsedShape( 3, 0, ShapesRegistry.getShape( 0 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 4, 0, ShapesRegistry.getShape( 2 ), 5 ) );
        calcEngine.addUsedShape( new UsedShape( 4, 2, ShapesRegistry.getShape( 10 ), 5 ) );
        calcEngine.addUsedShape( new UsedShape( 6, 0, ShapesRegistry.getShape( 7 ), 6 ) );
        calcEngine.addUsedShape( new UsedShape( 6, 3, ShapesRegistry.getShape( 1 ), 6 ) );
    }

    private static void puzzle018( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 0, 0, ShapesRegistry.getShape( 4 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 0, 1, ShapesRegistry.getShape( 9 ), 5 ) );
        calcEngine.addUsedShape( new UsedShape( 1, 3, ShapesRegistry.getShape( 3 ), 2 ) );
        calcEngine.addUsedShape( new UsedShape( 2, 0, ShapesRegistry.getShape( 7 ), 4 ) );
        calcEngine.addUsedShape( new UsedShape( 2, 2, ShapesRegistry.getShape( 11 ), 3 ) );
        calcEngine.addUsedShape( new UsedShape( 5, 3, ShapesRegistry.getShape( 8 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 9, 1, ShapesRegistry.getShape( 5 ), 7 ) );
    }

    private static void puzzle025( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 0, 1, ShapesRegistry.getShape( 9 ), 7 ) );
        calcEngine.addUsedShape( new UsedShape( 1, 3, ShapesRegistry.getShape( 0 ), 1 ) );
        calcEngine.addUsedShape( new UsedShape( 3, 2, ShapesRegistry.getShape( 11 ), 2 ) );
        calcEngine.addUsedShape( new UsedShape( 5, 3, ShapesRegistry.getShape( 8 ), 4 ) );
        calcEngine.addUsedShape( new UsedShape( 8, 3, ShapesRegistry.getShape( 7 ), 2 ) );
    }

    private static void puzzle031( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 0, 3, ShapesRegistry.getShape( 8 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 2, 1, ShapesRegistry.getShape( 11 ), 1 ) );
        calcEngine.addUsedShape( new UsedShape( 4, 2, ShapesRegistry.getShape( 6 ), 3 ) );
        calcEngine.addUsedShape( new UsedShape( 6, 2, ShapesRegistry.getShape( 4 ), 2 ) );
    }

    private static void puzzle037( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 0, 3, ShapesRegistry.getShape( 1 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 2, 1, ShapesRegistry.getShape( 5 ), 0 ) );
    }
}
