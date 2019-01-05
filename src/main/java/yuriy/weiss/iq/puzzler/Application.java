package yuriy.weiss.iq.puzzler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuriy.weiss.iq.puzzler.calc.CalcEngine;
import yuriy.weiss.iq.puzzler.calc.strategy.CheckAreasStrategy;
import yuriy.weiss.iq.puzzler.calc.strategy.WholeBoardStrategy;
import yuriy.weiss.iq.puzzler.model.ShapesRegistry;
import yuriy.weiss.iq.puzzler.model.UsedShape;

import java.util.Date;

public class Application {

    private static final Logger logger = LogManager.getLogger();

    public static void main( String[] args ) {
        logger.info( "running" );
        ShapesRegistry.printShapes();

        CalcEngine calcEngine = new CalcEngine( new CheckAreasStrategy() );
        //CalcEngine calcEngine = new CalcEngine( new WholeBoardStrategy() );

        excersize_037( calcEngine );

        calcEngine.printBoard();

        logger.info( "{} started", new Date() );
        calcEngine.placeNotUsedShapes();
        logger.info( "{} finished", new Date() );
    }

    private static void excersize_001( CalcEngine calcEngine ) {
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

    private static void excersize_018( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 0, 0, ShapesRegistry.getShape( 4 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 0, 1, ShapesRegistry.getShape( 9 ), 5 ) );
        calcEngine.addUsedShape( new UsedShape( 1, 3, ShapesRegistry.getShape( 3 ), 2 ) );
        calcEngine.addUsedShape( new UsedShape( 2, 0, ShapesRegistry.getShape( 7 ), 4 ) );
        calcEngine.addUsedShape( new UsedShape( 2, 2, ShapesRegistry.getShape( 11 ), 3 ) );
        calcEngine.addUsedShape( new UsedShape( 5, 3, ShapesRegistry.getShape( 8 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 9, 1, ShapesRegistry.getShape( 5 ), 7 ) );
    }

    private static void excersize_025( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 0, 1, ShapesRegistry.getShape( 9 ), 7 ) );
        calcEngine.addUsedShape( new UsedShape( 1, 3, ShapesRegistry.getShape( 0 ), 1 ) );
        calcEngine.addUsedShape( new UsedShape( 3, 2, ShapesRegistry.getShape( 11 ), 2 ) );
        calcEngine.addUsedShape( new UsedShape( 5, 3, ShapesRegistry.getShape( 8 ), 4 ) );
        calcEngine.addUsedShape( new UsedShape( 8, 3, ShapesRegistry.getShape( 7 ), 2 ) );
    }

    private static void excersize_031( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 0, 3, ShapesRegistry.getShape( 8 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 2, 1, ShapesRegistry.getShape( 11 ), 1 ) );
        calcEngine.addUsedShape( new UsedShape( 4, 2, ShapesRegistry.getShape( 6 ), 3 ) );
        calcEngine.addUsedShape( new UsedShape( 6, 2, ShapesRegistry.getShape( 4 ), 2 ) );
    }

    private static void excersize_037( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 0, 3, ShapesRegistry.getShape( 1 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 2, 1, ShapesRegistry.getShape( 5 ), 0 ) );
    }
}
