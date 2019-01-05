package yuriy.weiss.iq.puzzler.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ShapeVariant {
    private final Shape shape;
    private final int index;
    private final int[][] cells;
    private final int width;
    private final int height;

    // accelerator - coordinates of filled cells
    private final List<Cell> filledCells;

    public ShapeVariant( Shape shape, int index, int[][] cells ) {
        this.shape = shape;
        this.index = index;
        this.cells = cells;
        this.width = cells.length;
        this.height = cells[0].length;
        this.filledCells = Collections.unmodifiableList( buildFilledCells() );
    }

    private List<Cell> buildFilledCells() {
        List<Cell> result = new ArrayList<>();
        for ( int x = 0; x < width; x++ ) {
            for ( int y = 0; y < height; y++ ) {
                if ( cells[x][y] == 1 ) {
                    result.add( new Cell( x, y ) );
                }
            }
        }
        return result;
    }

    public Shape getShape() {
        return shape;
    }

    public int getIndex() {
        return index;
    }

    public int[][] getCells() {
        return cells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Cell> getFilledCells() {
        return filledCells;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        ShapeVariant that = ( ShapeVariant ) o;
        return index == that.index &&
                Objects.equals( shape, that.shape );
    }

    @Override
    public int hashCode() {
        return Objects.hash( shape, index );
    }

    @Override
    public String toString() {
        return "ShapeVariant{" +
                "shape=" + shape +
                ", index=" + index +
                '}';
    }
}
