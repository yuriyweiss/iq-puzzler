package yuriy.weiss.iq.puzzler.calc.strategy;

import yuriy.weiss.iq.puzzler.model.Board;
import yuriy.weiss.iq.puzzler.model.Cell;
import yuriy.weiss.iq.puzzler.model.CheckArea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckAreasSplitter {

    private final Board board;
    private Cell[][] freeCellsArray;
    private Set<Cell> freeCellsSet = new HashSet<>();
    private List<Set<Cell>> connectedAreas = new ArrayList<>();
    private List<CheckArea> checkAreas = new ArrayList<>();

    public CheckAreasSplitter( Board board ) {
        this.board = board;
        freeCellsArray = new Cell[board.getWidth()][board.getHeight()];
    }

    public void splitBoardToAreas() {
        buildFreeCells( board );
        splitFreeCellsToAreas();
        board.setConnectedAreas( connectedAreas );
        buildCheckAreas();
        board.setCheckAreas( checkAreas );
    }

    private void buildFreeCells( Board board ) {
        int[][] cells = board.getCells();
        for ( int x = 0; x < board.getWidth(); x++ ) {
            for ( int y = 0; y < board.getHeight(); y++ ) {
                if ( cells[x][y] == 0 ) {
                    Cell cell = new Cell( x, y );
                    freeCellsSet.add( cell );
                    freeCellsArray[x][y] = cell;
                } else {
                    freeCellsArray[x][y] = null;
                }
            }
        }
    }

    private void splitFreeCellsToAreas() {
        if ( freeCellsSet.isEmpty() ) {
            return;
        }
        Set<Cell> freeCellsArea;
        do {
            freeCellsArea = extractFreeCellsArea();
            if ( freeCellsArea != null ) {
                connectedAreas.add( freeCellsArea );
            }
        } while ( !freeCellsSet.isEmpty() || freeCellsArea == null );
    }

    private Set<Cell> extractFreeCellsArea() {
        Set<Cell> freeCellsArea = new HashSet<>();
        Cell cell = freeCellsSet.stream().findFirst().orElse( null );
        if ( cell == null ) {
            return Collections.emptySet();
        }
        freeCellsArea.add( cell );
        freeCellsSet.remove( cell );
        Set<Cell> foundNeighbours = new HashSet<>();
        foundNeighbours.add( cell );
        while ( !foundNeighbours.isEmpty() ) {
            Set<Cell> newNeighbours = findNewNeighbours( freeCellsArea, foundNeighbours );
            freeCellsArea.addAll( newNeighbours );
            freeCellsSet.removeAll( newNeighbours );
            foundNeighbours = newNeighbours;
        }
        return freeCellsArea;
    }

    private Set<Cell> findNewNeighbours( Set<Cell> freeCellsArea, Set<Cell> foundNeighbours ) {
        Set<Cell> newNeighbours = new HashSet<>();
        foundNeighbours.forEach( cell -> {
            int x = cell.getX();
            int y = cell.getY();
            tryAddNeighbour( x > 0 && freeCellsArray[x - 1][y] != null, x - 1, y, freeCellsArea,
                    newNeighbours );
            tryAddNeighbour( x < board.getWidth() - 1 && freeCellsArray[x + 1][y] != null, x + 1, y, freeCellsArea,
                    newNeighbours );
            tryAddNeighbour( y > 0 && freeCellsArray[x][y - 1] != null, x, y - 1, freeCellsArea, newNeighbours );
            tryAddNeighbour( y < board.getHeight() - 1 && freeCellsArray[x][y + 1] != null, x, y + 1, freeCellsArea,
                    newNeighbours );
        } );
        return newNeighbours;
    }

    private void tryAddNeighbour( boolean neighbourExists, int x, int y, Set<Cell> freeCellsArea,
            Set<Cell> newNeighbours ) {
        if ( neighbourExists ) {
            // get real cell only here to avoid array index out of bounds error
            Cell neighbour = freeCellsArray[x][y];
            if ( !freeCellsArea.contains( neighbour ) ) {
                newNeighbours.add( neighbour );
            }
        }
    }

    private void buildCheckAreas() {
        connectedAreas.forEach( connectedArea -> {
            int minX = connectedArea.stream().min( Comparator.comparingInt( Cell::getX ) ).orElse( null ).getX();
            int maxX = connectedArea.stream().max( Comparator.comparingInt( Cell::getX ) ).orElse( null ).getX();
            int minY = connectedArea.stream().min( Comparator.comparingInt( Cell::getY ) ).orElse( null ).getY();
            int maxY = connectedArea.stream().max( Comparator.comparingInt( Cell::getY ) ).orElse( null ).getY();
            checkAreas.add( new CheckArea( minX, maxX, minY, maxY ) );
        } );
    }
}
