package yuriy.weiss.iq.puzzler.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {

    private final int[][] cells;
    private List<UsedShape> usedShapes;
    private final int width;
    private final int height;
    private List<Set<Cell>> connectedAreas;
    private List<CheckArea> checkAreas;
    private Set<Cell> possibleCells = new HashSet<>();

    public Board( int width, int height ) {
        cells = new int[width][height];
        for ( int x = 0; x < width; x++ ) {
            for ( int y = 0; y < height; y++ ) {
                cells[x][y] = 0;
            }
        }
        usedShapes = new ArrayList<>();
        this.width = width;
        this.height = height;
    }

    public Board( Board source ) {
        this( source.width, source.height );
        for ( int x = 0; x < width; x++ ) {
            for ( int y = 0; y < height; y++ ) {
                cells[x][y] = source.getCells()[x][y];
            }
        }
        source.getUsedShapes().forEach( usedShape -> usedShapes.add( new UsedShape( usedShape ) ) );
    }

    public int[][] getCells() {
        return cells;
    }

    public List<UsedShape> getUsedShapes() {
        return usedShapes;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addUsedShape( UsedShape usedShape ) {
        int x = usedShape.getX();
        int y = usedShape.getY();
        int[][] shapeCells = usedShape.getCells();
        for ( int x1 = 0; x1 < usedShape.getWidth(); x1++ ) {
            for ( int y1 = 0; y1 < usedShape.getHeight(); y1++ ) {
                if ( shapeCells[x1][y1] == 1 ) {
                    cells[x + x1][y + y1] = 1;
                }
            }
        }
        usedShapes.add( usedShape );
    }

    public void setConnectedAreas( List<Set<Cell>> connectedAreas ) {
        this.connectedAreas = connectedAreas;
    }

    public int getMinConnectedAreaSize() {
        // get minimum connected area size
        return connectedAreas.stream()
                .min( Comparator.comparingInt( Set::size ) )
                .orElse( Collections.emptySet() )
                .size();
    }

    public void setCheckAreas( List<CheckArea> checkAreas ) {
        this.checkAreas = checkAreas;
    }

    public void buildPossibleCells() {
        checkAreas.forEach( checkArea -> {
            for ( int x = checkArea.getLeft(); x <= checkArea.getRight() - 1; x++ ) {
                for ( int y = checkArea.getBottom(); y <= checkArea.getTop() - 1; y++ ) {
                    possibleCells.add( new Cell( x, y ) );
                }
            }
        } );
    }

    public Set<Cell> getPossibleCells() {
        return possibleCells;
    }

    public String print() {
        String[][] symbols = new String[width][height];
        for ( int x = 0; x < width; x++ ) {
            for ( int y = 0; y < height; y++ ) {
                symbols[x][y] = cells[x][y] == 0 ? "0" : "X";
            }
        }
        usedShapes.forEach( usedShape -> replaceSymbols( symbols, usedShape ) );

        StringBuilder sb = new StringBuilder();
        for ( int y = symbols[0].length - 1; y >= 0; y-- ) {
            for ( int x = 0; x < symbols.length; x++ ) {
                sb.append( symbols[x][y] ).append( " " );
            }
            sb.append( "\n" );
        }
        return "\n" + sb.toString();
    }

    private void replaceSymbols( String[][] symbols, UsedShape usedShape ) {
        int x = usedShape.getX();
        int y = usedShape.getY();
        int[][] shapeCells = usedShape.getCells();
        for ( int x1 = 0; x1 < usedShape.getWidth(); x1++ ) {
            for ( int y1 = 0; y1 < usedShape.getHeight(); y1++ ) {
                if ( shapeCells[x1][y1] == 1 ) {
                    symbols[x + x1][y + y1] = usedShape.getSymbol();
                }
            }
        }
    }
}
