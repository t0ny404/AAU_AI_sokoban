import java.util.*;

import Utils.Node;
import Utils.State;
import Utils.Utils;
import cz.sokoban4j.simulation.actions.EDirection;
import cz.sokoban4j.simulation.board.compact.BoardCompact;
import cz.sokoban4j.simulation.board.compact.CTile;


public class AgentIDAs extends AgentAS {

	private static final int SUCCESS = -1;

	@Override
	protected List<EDirection> think (BoardCompact board) {
		System.out.println("\nIDA*\n");

		this.board = board;
		step = 0;
		result = getResult(IDAStar());
		if (result.size() == 0) {
			throw new RuntimeException("FAILED TO SOLVE THE BOARD...");
		}

		System.out.println(step);

		return result;
	}


	private PriorityQueue<State> successor(State current) {
//		BoardCompact board = state.getBoard();
//
//		HashSet<Node> explored = new HashSet<>();
//		Queue<Node> fringe = new LinkedList<>();
//
//		PriorityQueue<State> result = new PriorityQueue<>(Comparator.comparingInt(State::getF));
//
//		Node start = new Node(board.playerX, board.playerY);
//		fringe.add(start);
//		while (!fringe.isEmpty()) {
//
//			step++;
//
//			Node current = fringe.poll();
//			if (!explored.contains(current)) {
//				explored.add(current);
//
//				for (EDirection direction : EDirection.arrows()) {
//					Node next = new Node(current, direction);
//
//					if (!explored.contains(next) && !CTile.isWall(board.tile(next.getX(), next.getY()))) {
//						if (!CTile.isSomeBox(board.tile(next.getX(), next.getY()))) {
//							fringe.add(next);
//						} else if (!CTile.isSomeBox(board.tile(next.getX() + direction.dX, next.getY() + direction.dY))
//									&& !isStrait(next.getX() + direction.dX, next.getY() + direction.dY)) {
//							while (next.getParent() != null) {
//								if (next.getParent().getParent() == null) {
//									BoardCompact nextBoard = Utils.advance(state.getBoard(), next.getDirection());
//									result.add(new State(nextBoard, next.getDirection(), state.getG() + 1, h(nextBoard)));
//								}
//								next = next.getParent();
//							}
//						}
//					}
//				}
//			}
//		}
//		return result;
		PriorityQueue<State> fringe = new PriorityQueue<>(Comparator.comparingInt(State::getF));
		fringe.add(current);

		step++;

		Node currentNode = new Node(current.getBoard().playerX, current.getBoard().playerY);

		for (EDirection direction: EDirection.arrows()) {
			Node next = new Node(currentNode, direction);

			if (!CTile.isWall(board.tile(next.getX(), next.getY()))) {
				if (!CTile.isSomeBox(board.tile(next.getX(), next.getY())) || (CTile.isSomeBox(board.tile(next.getX(), next.getY())) && !CTile.isSomeBox(board.tile(next.getX() + direction.dX, next.getY() + direction.dY))
									&& !isStrait(next.getX() + direction.dX, next.getY() + direction.dY))) {

					State child = new State(Utils.advance(current.getBoard(), direction), direction, current);
					child.setH(h(child.getBoard()));

					if (!fringe.contains(child))
						fringe.add(child);
				}
			}
		}
		return fringe;
	}

	private Boolean isStrait(int x, int y) {
		return CTile.isWall(board.tile(x, y)) || (!CTile.forSomeBox(board.tile(x, y)) && (
				(CTile.isWall(board.tile(x + 1, y)) && (CTile.isWall(board.tile(x, y + 1)) || CTile.isWall(board.tile(x, y - 1)))) ||
						(CTile.isWall(board.tile(x - 1, y)) && (CTile.isWall(board.tile(x, y + 1)) || CTile.isWall(board.tile(x, y - 1))))));
	}

	private Integer search(Stack<State> path, Integer bound) {
		State state = path.peek();

		if (state.getF() > bound)
			return state.getF();

		if (Utils.isGoal(state))
			return SUCCESS;

		int min = Integer.MAX_VALUE;

		for (State succ: successor(state)) {
			if (!path.contains(succ)) {
				path.push(succ);

				Integer t = search(path, bound);

				if (t == SUCCESS) return t;
				if (t < min) min = t;
				path.pop();
			}
		}
		return min;
	}

	protected Stack<State> IDAStar() {
		Integer bound = h(board);
		State root = new State(board, null, 0, bound);

		Stack<State> path = new Stack<>();
		path.push(root);
		while (true) {
			Integer t = search(path, bound);
			if (t == SUCCESS)
				return path;
			if (t == Integer.MAX_VALUE)
				return new Stack<>();
			bound = t;
		}
	}

	private List<EDirection> getResult(Stack<State> path) {
		result = new ArrayList<>();
		while (!path.empty()) {
			result.add(path.pop().getDirection());
		}
		Collections.reverse(result);
		return result;
	}
}