package yuriy.weiss.iq.puzzler.calc.strategy;

import yuriy.weiss.iq.puzzler.model.Board;

public interface BoardPreparationStrategy {
    void prepareBoardBeforePlacement(Board board);
}
