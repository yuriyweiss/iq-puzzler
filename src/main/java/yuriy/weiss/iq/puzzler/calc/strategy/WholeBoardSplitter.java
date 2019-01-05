package yuriy.weiss.iq.puzzler.calc.strategy;

import yuriy.weiss.iq.puzzler.model.Board;
import yuriy.weiss.iq.puzzler.model.Cell;
import yuriy.weiss.iq.puzzler.model.CheckArea;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WholeBoardSplitter {

    private final Board board;
    private Cell[][] freeCellsArray;
    private Set<Cell> freeCellsSet = new HashSet<>();
    private List<Set<Cell>> connectedAreas = new ArrayList<>();
    private List<CheckArea> checkAreas = new ArrayList<>();

    public WholeBoardSplitter( Board board ) {
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
        Set<Cell> freeCellsArea = new HashSet<>();
        freeCellsArea.addAll( freeCellsSet );
        connectedAreas.add( freeCellsArea );
    }

    private void buildCheckAreas() {
        checkAreas.add( new CheckArea( 0, board.getWidth() - 1, 0, board.getHeight() - 1 ) );
    }
}
