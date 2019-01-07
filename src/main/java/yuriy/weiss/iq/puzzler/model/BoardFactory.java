package yuriy.weiss.iq.puzzler.model;

import yuriy.weiss.iq.puzzler.PuzzlerException;

public class BoardFactory {

    private BoardFactory() {
    }

    public static Board createBoard( int puzzleNumber ) {
        if ( puzzleNumber <= 40 ) {
            return createBasicBoard();
        } else if ( puzzleNumber <= 80 ) {
            return createRhombusBoard();
        } else {
            throw new PuzzlerException( "illegal puzzle number: " + puzzleNumber );
        }
    }

    private static Board createBasicBoard() {
        return new Board( 11, 5 );
    }

    private static Board createRhombusBoard() {
        Board board = new Board( 9, 9 );
        int[][] cells = board.getCells();
        cells[0][8] = 1;
        cells[8][0] = 1;
        fillCells( cells, 0, 0, 3, 1 );
        fillCells( cells, 0, 2, 1, 3 );
        fillCells( cells, 5, 7, 8, 8 );
        fillCells( cells, 7, 5, 8, 6 );
        return board;
    }

    private static void fillCells( int[][] cells, int x0, int y0, int x1, int y1 ) {
        for ( int x = x0; x <= x1; x++ ) {
            for ( int y = y0; y <= y1; y++ ) {
                cells[x][y] = 1;
            }
        }
    }
}
