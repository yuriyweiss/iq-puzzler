package yuriy.weiss.iq.puzzler.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShapesRegistry {

    private static final Logger logger = LogManager.getLogger();

    private static final List<Shape> shapes = new ArrayList<>();

    private ShapesRegistry() {}

    static {
        shapes.add( new Shape( new int[][]{
                { 1, 1 },
                { 1, 0 }
        }, 0, false, "A" ) );
        shapes.add( new Shape( new int[][]{
                { 1, 1 },
                { 1, 0 },
                { 1, 0 }
        }, 1, true, "B" ) );
        shapes.add( new Shape( new int[][]{
                { 0, 1 },
                { 1, 1 },
                { 1, 0 }
        }, 2, true, "C" ) );
        shapes.add( new Shape( new int[][]{
                { 1, 0 },
                { 1, 1 },
                { 1, 0 }
        }, 3, false, "D" ) );
        shapes.add( new Shape( new int[][]{
                { 1, 1, 1 },
                { 1, 0, 0 },
                { 1, 0, 0 }
        }, 4, false, "E" ) );
        shapes.add( new Shape( new int[][]{
                { 1, 1 },
                { 1, 0 },
                { 1, 0 },
                { 1, 0 }
        }, 5, true, "F" ) );
        shapes.add( new Shape( new int[][]{
                { 1, 1 },
                { 1, 0 },
                { 1, 1 }
        }, 6, false, "G" ) );
        shapes.add( new Shape( new int[][]{
                { 1, 1 },
                { 1, 1 },
                { 0, 1 }
        }, 7, true, "H" ) );
        shapes.add( new Shape( new int[][]{
                { 0, 1 },
                { 0, 1 },
                { 1, 1 },
                { 0, 1 }
        }, 8, true, "I" ) );
        shapes.add( new Shape( new int[][]{
                { 0, 1 },
                { 0, 1 },
                { 1, 1 },
                { 1, 0 }
        }, 9, true, "J" ) );
        shapes.add( new Shape( new int[][]{
                { 0, 1, 1 },
                { 1, 1, 0 },
                { 0, 1, 0 }
        }, 10, true, "K" ) );
        shapes.add( new Shape( new int[][]{
                { 0, 1, 1 },
                { 1, 1, 0 },
                { 1, 0, 0 }
        }, 11, false, "L" ) );
    }

    public static List<Shape> getShapes() {
        return Collections.unmodifiableList( shapes );
    }


    public static Shape getShape( int shapeIndex ) {
        return shapes.get( shapeIndex );
    }

    public static void printShapes() {
        shapes.forEach( shape -> {
            int count = shape.getVariantsCount();
            logger.info( "--------------------------------------" );
            logger.info( "SHAPE: {}", shape.getId() );
            for ( int i = 0; i < count; i++ ) {
                logger.info( Integer.toString( i ) );
                logger.info( shape.printVariantCells( i ) );
            }
        } );
    }
}
