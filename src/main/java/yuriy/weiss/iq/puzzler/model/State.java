package yuriy.weiss.iq.puzzler.model;

import java.util.ArrayList;
import java.util.List;

public class State {
    private Board board;
    private List<Shape> notUsedShapes = new ArrayList<>();
    private boolean placementSuccess = false;

    public State( Board board, List<Shape> notUsedShapes ) {
        this.board = new Board( board );
        this.notUsedShapes.addAll( notUsedShapes );
    }

    public boolean isPlacementSuccess() {
        return placementSuccess;
    }

    public void setPlacementSuccess(boolean placementSuccess) {
        this.placementSuccess = placementSuccess;
    }

    public Board getBoard() {
        return board;
    }

    public List<Shape> getNotUsedShapes() {
        return notUsedShapes;
    }
}
