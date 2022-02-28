import java.util.*;

import Utils.Utils;
import Utils.State;
import cz.sokoban4j.agents.ArtificialAgent;
import cz.sokoban4j.simulation.actions.EDirection;
import cz.sokoban4j.simulation.board.compact.BoardCompact;

public class AgentBFS extends ArtificialAgent {

    protected List<EDirection> result;
    protected BoardCompact board;

    protected int step;

    @Override
    protected List<EDirection> think(BoardCompact board) {
        System.out.println("\nBFS\n");

        this.board = board;
        step = 0;
        result = BFS();
        if (result.size() == 0) {
            throw new RuntimeException("FAILED TO SOLVE THE BOARD...");
        }

        System.out.println(step);

        return result;
    }

    protected List<EDirection> BFS() {
        State start = new State(board.clone());

        HashSet<State> explored = new HashSet<>();
        explored.add(start);

        Queue<State> fringe = new LinkedList<>();
        fringe.add(start);

        while (!fringe.isEmpty()) {
            State current = fringe.poll();

            step++;

            if (Utils.isGoal(current))
                return getResult(current);

            for (EDirection direction : EDirection.arrows()) {
                State child = new State(Utils.advance(current.getBoard(), direction), direction, current);

                if (!explored.contains(child) && !fringe.contains(child)) {
                    fringe.add(child);
                }
            }
        }
        return new ArrayList<>();
    }

    private List<EDirection> getResult(State node) {
        List<EDirection> result = new ArrayList<>();
        while (node.getParent() != null) {
            result.add(node.getDirection());
            node = node.getParent();
        }
        Collections.reverse(result);
        return result;
    }
}