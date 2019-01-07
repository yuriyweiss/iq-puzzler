package yuriy.weiss.iq.puzzler.calc.strategy;

import yuriy.weiss.iq.puzzler.model.Board;

public interface BoardPreparationStrategy {

    /**
     * Prepare board placement accelerator structures before calculations.<br>
     * WholeBoardStrategy checks all available board cells.<br>
     * CheckAreasStrategy builds connected areas of free cells, then checks board cells only from that areas.
     *
     * @param board board to prepare
     */
    void prepareBoardBeforePlacement( Board board );
}
