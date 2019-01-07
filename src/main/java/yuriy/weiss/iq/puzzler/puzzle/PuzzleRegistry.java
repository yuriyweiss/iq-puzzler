package yuriy.weiss.iq.puzzler.puzzle;

import yuriy.weiss.iq.puzzler.PuzzlerException;
import yuriy.weiss.iq.puzzler.calc.CalcEngine;
import yuriy.weiss.iq.puzzler.model.ShapesRegistry;
import yuriy.weiss.iq.puzzler.model.UsedShape;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PuzzleRegistry {

    private PuzzleRegistry() {
    }

    public static void addUsedShapesForPuzzle( String puzzleId, CalcEngine calcEngine ) {
        try {
            Method puzzleMethod = PuzzleRegistry.class.getDeclaredMethod( "puzzle" + puzzleId, CalcEngine.class );
            puzzleMethod.invoke( PuzzleRegistry.class, calcEngine );
        } catch ( NoSuchMethodException | IllegalAccessException | InvocationTargetException e ) {
            throw new PuzzlerException( "puzzle method with id [" + puzzleId + "] call exception", e );
        }
    }

    public static void puzzle001( CalcEngine calcEngine ) {
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

    public static void puzzle018( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 0, 0, ShapesRegistry.getShape( 4 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 0, 1, ShapesRegistry.getShape( 9 ), 5 ) );
        calcEngine.addUsedShape( new UsedShape( 1, 3, ShapesRegistry.getShape( 3 ), 2 ) );
        calcEngine.addUsedShape( new UsedShape( 2, 0, ShapesRegistry.getShape( 7 ), 4 ) );
        calcEngine.addUsedShape( new UsedShape( 2, 2, ShapesRegistry.getShape( 11 ), 3 ) );
        calcEngine.addUsedShape( new UsedShape( 5, 3, ShapesRegistry.getShape( 8 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 9, 1, ShapesRegistry.getShape( 5 ), 7 ) );
    }

    public static void puzzle025( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 0, 1, ShapesRegistry.getShape( 9 ), 7 ) );
        calcEngine.addUsedShape( new UsedShape( 1, 3, ShapesRegistry.getShape( 0 ), 1 ) );
        calcEngine.addUsedShape( new UsedShape( 3, 2, ShapesRegistry.getShape( 11 ), 2 ) );
        calcEngine.addUsedShape( new UsedShape( 5, 3, ShapesRegistry.getShape( 8 ), 4 ) );
        calcEngine.addUsedShape( new UsedShape( 8, 3, ShapesRegistry.getShape( 7 ), 2 ) );
    }

    public static void puzzle031( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 0, 3, ShapesRegistry.getShape( 8 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 2, 1, ShapesRegistry.getShape( 11 ), 1 ) );
        calcEngine.addUsedShape( new UsedShape( 4, 2, ShapesRegistry.getShape( 6 ), 3 ) );
        calcEngine.addUsedShape( new UsedShape( 6, 2, ShapesRegistry.getShape( 4 ), 2 ) );
    }

    public static void puzzle037( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 0, 3, ShapesRegistry.getShape( 1 ), 0 ) );
        calcEngine.addUsedShape( new UsedShape( 2, 1, ShapesRegistry.getShape( 5 ), 0 ) );
    }

    public static void puzzle041( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 2, 0, ShapesRegistry.getShape( 4 ), 2 ) );
        calcEngine.addUsedShape( new UsedShape( 2, 3, ShapesRegistry.getShape( 1 ), 3 ) );
        calcEngine.addUsedShape( new UsedShape( 3, 7, ShapesRegistry.getShape( 0 ), 1 ) );
        calcEngine.addUsedShape( new UsedShape( 4, 3, ShapesRegistry.getShape( 3 ), 1 ) );
        calcEngine.addUsedShape( new UsedShape( 4, 5, ShapesRegistry.getShape( 2 ), 5 ) );
        calcEngine.addUsedShape( new UsedShape( 5, 0, ShapesRegistry.getShape( 5 ), 1 ) );
        calcEngine.addUsedShape( new UsedShape( 6, 0, ShapesRegistry.getShape( 7 ), 3 ) );
        calcEngine.addUsedShape( new UsedShape( 6, 3, ShapesRegistry.getShape( 9 ), 7 ) );
        calcEngine.addUsedShape( new UsedShape( 7, 1, ShapesRegistry.getShape( 8 ), 1 ) );
    }

    public static void puzzle073( CalcEngine calcEngine ) {
        calcEngine.addUsedShape( new UsedShape( 3, 4, ShapesRegistry.getShape( 0 ), 2 ) );
        calcEngine.addUsedShape( new UsedShape( 3, 6, ShapesRegistry.getShape( 7 ), 1 ) );
        calcEngine.addUsedShape( new UsedShape( 5, 3, ShapesRegistry.getShape( 5 ), 1 ) );
    }
}
