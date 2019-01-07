package yuriy.weiss.iq.puzzler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuriy.weiss.iq.puzzler.calc.CalcEngine;
import yuriy.weiss.iq.puzzler.calc.strategy.CheckAreasStrategy;
import yuriy.weiss.iq.puzzler.model.BoardFactory;
import yuriy.weiss.iq.puzzler.model.ShapesRegistry;
import yuriy.weiss.iq.puzzler.puzzle.PuzzleRegistry;

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

        int puzzleNumber = Integer.parseInt( puzzleId );
        CalcEngine calcEngine = new CalcEngine( BoardFactory.createBoard( puzzleNumber ),
                new CheckAreasStrategy(), 6, 6 );
        // CalcEngine calcEngine = new CalcEngine( new WholeBoardStrategy(), 4 )

        PuzzleRegistry.addUsedShapesForPuzzle( puzzleId, calcEngine );

        calcEngine.printBoard();

        logger.info( "started" );
        calcEngine.startPlacementThreads();
    }
}
