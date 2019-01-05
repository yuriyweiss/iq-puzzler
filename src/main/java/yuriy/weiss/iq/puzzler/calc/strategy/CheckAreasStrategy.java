package yuriy.weiss.iq.puzzler.calc.strategy;

import yuriy.weiss.iq.puzzler.kpi.KpiHolder;
import yuriy.weiss.iq.puzzler.model.Board;

public class CheckAreasStrategy implements BoardPreparationStrategy {

    @Override
    public void prepareBoardBeforePlacement( Board board ) {
        long startTime = System.currentTimeMillis();
        new CheckAreasSplitter( board ).splitBoardToAreas();
        board.buildPossibleCells();
        KpiHolder.getPreparationTimeKpi().inc( System.currentTimeMillis() - startTime );
    }
}
