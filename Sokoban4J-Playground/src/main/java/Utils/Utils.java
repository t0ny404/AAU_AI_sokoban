package Utils;

import cz.sokoban4j.simulation.actions.EDirection;
import cz.sokoban4j.simulation.actions.compact.CMove;
import cz.sokoban4j.simulation.actions.compact.CPush;
import cz.sokoban4j.simulation.board.compact.BoardCompact;

public class Utils {

    public static BoardCompact advance(BoardCompact board, EDirection action) {
        CMove move = CMove.getAction(action);
        if (move.isPossible(board)) {
            move.perform(board);
        }
        else {
            CPush push = CPush.getAction(action);
            if (push.isPossible(board)){
                push.perform(board);
            }
        }
        return board;
    }

    public static boolean isGoal(State state) {
        return state.getBoard().isVictory();
    }
}
