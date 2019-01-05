package yuriy.weiss.iq.puzzler.model;

public class CheckArea {
    private int left;
    private int right;
    private int bottom;
    private int top;

    public CheckArea( int left, int right, int bottom, int top ) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft( int left ) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight( int right ) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom( int bottom ) {
        this.bottom = bottom;
    }

    public int getTop() {
        return top;
    }

    public void setTop( int top ) {
        this.top = top;
    }
}
