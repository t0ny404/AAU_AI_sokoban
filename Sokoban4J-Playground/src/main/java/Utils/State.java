package Utils;

import cz.sokoban4j.simulation.actions.EDirection;
import cz.sokoban4j.simulation.board.compact.BoardCompact;

public class State {

    private BoardCompact board;
    private EDirection direction;

    private State parent;

    private Integer g;
    private Integer h;
    private Integer f;

    public State(BoardCompact board, EDirection direction, Integer g, Integer h) {
        this.board = board;
        this.direction = direction;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    public State(BoardCompact board, EDirection direction, State parent) {
        this.board = board;
        this.direction = direction;
        this.g = parent.getG() + 1;
        this.parent = parent;
    }

    public State(BoardCompact board) {
        this.board = board;
        this.direction = null;
        this.g = 0;
        this.parent = null;
    }

    public BoardCompact getBoard() {
        return board.clone();
    }

    public void setBoard(BoardCompact board) {
        this.board = board;
    }

    public EDirection getDirection() {
        return direction;
    }

    public void setDirection(EDirection direction) {
        this.direction = direction;
    }

    public Integer getG() {
        return g;
    }

    public void setG(Integer g) {
        this.g = g;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
        this.f = g + h;
    }

    public Integer getF() {
        return f;
    }

    public void setF(Integer f) {
        this.f = f;
    }

    public State getParent() {
        return parent;
    }

    public void setParent(State parent) {
        this.parent = parent;
    }

    public void update(State state) {
        this.parent = state.getParent();
        this.g = state.getG();
        this.f = state.getF();
        this.direction = state.direction;
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State o = (State) obj;
            return board.equals(o.board);
        }
        return false;
    }
}
