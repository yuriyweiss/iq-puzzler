package yuriy.weiss.iq.puzzler.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class Shape {

    private int id;
    private int width;
    private int height;
    private String symbol;
    private int size;

    private List<ShapeVariant> variants = new ArrayList<>();

    public Shape( int[][] cellsMask, int id, boolean hasMirror, String symbol ) {
        this.width = cellsMask.length;
        this.height = cellsMask[0].length;
        this.id = id;
        this.symbol = symbol;
        this.size = calcSize( cellsMask );
        initVariants( cellsMask, hasMirror );
    }

    private int calcSize( int[][] cellsMask ) {
        int result = 0;
        for ( int x = 0; x < width; x++ ) {
            for ( int y = 0; y < height; y++ ) {
                result += cellsMask[x][y];
            }
        }
        return result;
    }

    private void initVariants( int[][] cellsMask, boolean hasMirror ) {
        buildAllRotations( cellsMask );
        if ( hasMirror ) {
            int[][] mirroredMask = buildMirror( cellsMask );
            buildAllRotations( mirroredMask );
        }
    }

    private void buildAllRotations( int[][] cellsMask ) {
        addShapeVariant( variants, () -> {
            int[][] variantCells = new int[width][height];
            for ( int x = 0; x < width; x++ ) {
                for ( int y = 0; y < height; y++ ) {
                    variantCells[x][y] = cellsMask[x][y];
                }
            }
            return variantCells;
        } );
        addShapeVariant( variants, () -> {
            int[][] variantCells = new int[height][width];
            for ( int x = 0; x < height; x++ ) {
                for ( int y = 0; y < width; y++ ) {
                    variantCells[x][y] = cellsMask[width - 1 - y][x];
                }
            }
            return variantCells;
        } );
        addShapeVariant( variants, () -> {
            int[][] variantCells = new int[width][height];
            for ( int x = 0; x < width; x++ ) {
                for ( int y = 0; y < height; y++ ) {
                    variantCells[x][y] = cellsMask[width - 1 - x][height - 1 - y];
                }
            }
            return variantCells;
        } );
        addShapeVariant( variants, () -> {
            int[][] variantCells = new int[height][width];
            for ( int x = 0; x < height; x++ ) {
                for ( int y = 0; y < width; y++ ) {
                    variantCells[x][y] = cellsMask[y][height - 1 - x];
                }
            }
            return variantCells;
        } );
    }

    private void addShapeVariant( List<ShapeVariant> variants, Supplier<int[][]> cellsBuilder ) {
        int variantIndex = variants.size();
        int[][] variantCells = cellsBuilder.get();
        variants.add( new ShapeVariant( this, variantIndex, variantCells ) );
    }

    private int[][] buildMirror( int[][] cellsMask ) {
        int[][] mirror = new int[width][height];
        for ( int x = 0; x < width; x++ ) {
            for ( int y = 0; y < height; y++ ) {
                mirror[width - 1 - x][y] = cellsMask[x][y];
            }
        }
        return mirror;
    }


    public ShapeVariant getVariant( int variantIndex ) {
        return variants.get( variantIndex );
    }

    // as shape rotates by 90 degree from variant to variant, it's width and height
    // are flipped each time
    public int getWidth( int variantIndex ) {
        return getVariant( variantIndex ).getWidth();
    }

    public int getHeight( int variantIndex ) {
        return getVariant( variantIndex ).getHeight();
    }

    public int getId() {
        return id;
    }

    public List<ShapeVariant> getVariants() {
        return variants;
    }

    public int getVariantsCount() {
        return variants.size();
    }

    public String getSymbol() {
        return symbol;
    }

    public int getSize() {
        return size;
    }

    public String printVariantCells( int variantIndex ) {
        int[][] cells = getVariant( variantIndex ).getCells();
        StringBuilder sb = new StringBuilder();
        for ( int y = cells[0].length - 1; y >= 0; y-- ) {
            for ( int x = 0; x < cells.length; x++ ) {
                String value = cells[x][y] == 0 ? "0" : symbol;
                sb.append( value ).append( " " );
            }
            sb.append( "\n" );
        }
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Shape shape = ( Shape ) o;
        return id == shape.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }

    @Override
    public String toString() {
        return "Shape{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
