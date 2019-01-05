package yuriy.weiss.iq.puzzler.model;

public class UsedShape {
    private int x;
    private int y;
    private Shape shape;
    private int shapeVariant;

    public UsedShape( int x, int y, Shape shape, int shapeVariant ) {
        this.x = x;
        this.y = y;
        this.shape = shape;
        this.shapeVariant = shapeVariant;
    }

    public UsedShape( UsedShape usedShape ) {
        this( usedShape.x, usedShape.y, usedShape.shape, usedShape.shapeVariant );
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Shape getShape() {
        return shape;
    }

    public int getShapeVariant() {
        return shapeVariant;
    }

    public int[][] getCells() {
        return shape.getVariant( shapeVariant ).getCells();
    }

    public int getWidth() {
        return shape.getWidth( shapeVariant );
    }

    public int getHeight() {
        return shape.getHeight( shapeVariant );
    }

    public String getSymbol() {
        return shape.getSymbol();
    }
}
