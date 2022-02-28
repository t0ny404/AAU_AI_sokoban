package Utils;

import cz.sokoban4j.simulation.actions.EDirection;

import static java.util.Objects.hash;

public class Node {

    private Integer x;
    private Integer y;
    private Node parent;
    private EDirection direction;

    public Node(Integer x, Integer y, Node parent, EDirection direction) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.direction = direction;
    }

    public Node(Node parent, EDirection direction) {
        this.parent = parent;
        this.direction = direction;
        this.x = parent.getX() + direction.dX;
        this.y = parent.getY() + direction.dY;
    }

    public Node(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
         if (obj instanceof Node) {
             Node o = (Node) obj;
             return this.x.equals(o.x) && this.y.equals(o.y);
         }
         return false;
    }

    @Override
    public int hashCode() {
        return hash(x, y);
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public EDirection getDirection() {
        return direction;
    }

    public void setDirection(EDirection action) {
        this.direction = action;
    }
}
