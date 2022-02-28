import Utils.State;
import Utils.Utils;
import Utils.Node;
import cz.sokoban4j.agents.ArtificialAgent;
import cz.sokoban4j.simulation.actions.EDirection;
import cz.sokoban4j.simulation.board.compact.BoardCompact;
import cz.sokoban4j.simulation.board.compact.CTile;

import java.util.*;

public class AgentAS extends ArtificialAgent {

    protected List<EDirection> result;
    protected BoardCompact board;

    protected int step;

    @Override
    protected List<EDirection> think(BoardCompact board) {
        System.out.println("\nA*\n");

        this.board = board;
        step = 0;
        result = getResult(AStar());
        if (result.size() == 0) {
            throw new RuntimeException("FAILED TO SOLVE THE BOARD...");
        }

        System.out.println(step);

        return result;
    }

    protected State AStar() {
        State start = new State(board.clone());

        HashMap<State, State> explored = new HashMap<>();
        explored.put(start, start);

        PriorityQueue<State> fringe = new PriorityQueue<>(Comparator.comparingInt(State::getF));
        fringe.add(start);

        while(!fringe.isEmpty()) {
            State current = fringe.poll();

            step++;

            if (Utils.isGoal(current))
                return current;

            for (EDirection direction: EDirection.arrows()) {
                State child = new State(Utils.advance(current.getBoard(), direction), direction, current);

                if (!explored.containsKey(child) || (child.getG() < explored.get(child).getG())) {
                    child.setH(h(child.getBoard()));

                    if (!explored.containsKey(child)) explored.put(child, child);
                    else explored.get(child).update(child);

                    if (!fringe.contains(child))
                        fringe.add(child);
                }
            }
        }
        return null;
    }

    protected List<EDirection> getResult(State state) {
        List<EDirection> result = new ArrayList<>();
        while (state != null) {
            result.add(state.getDirection());
            state = state.getParent();
        }
        Collections.reverse(result);
        return result;
    }

    protected Integer h(BoardCompact brd) {
        List<Node> boxes = new ArrayList<>();
        List<Node> targets = new ArrayList<>();
        for (int y = 0; y < brd.height(); ++y) {
            for (int x = 0; x < brd.width(); ++x) {
                if (CTile.isSomeBox(brd.tile(x, y))) {
                    boxes.add(new Node(x, y));
                }
                if (CTile.forSomeBox(brd.tile(x, y))) {
                    targets.add(new Node(x, y));
                }
            }
        }
        int closestBoxToPlayer = boxes.stream().mapToInt(b -> Math.abs(b.getX()- brd.playerX) + Math.abs(b.getY() - brd.playerY)).min().orElse(999);
        int totalClosestTargetToBoxes = boxes.stream().mapToInt(b -> targets.stream().mapToInt(t -> Math.abs(b.getX() - t.getX()) + Math.abs(b.getY() - t.getY())).min().orElse(999)).sum();
        return totalClosestTargetToBoxes + closestBoxToPlayer;
    }
}