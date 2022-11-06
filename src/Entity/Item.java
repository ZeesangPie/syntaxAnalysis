package Entity;

import java.util.List;
import java.util.Objects;

public class Item {
    private String left;
    private List<String> right;
    private int dot;

    public Item(String left, List<String> right, int dot) {
        this.left = left;
        this.right = right;
        this.dot = dot;
    }

    public Item(Product p, int dot) {
        this.left = p.getLeft();
        this.right = p.getRight();
        this.dot = dot;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public List<String> getRight() {
        return right;
    }

    public void setRight(List<String> right) {
        this.right = right;
    }

    public int getDot() {
        return dot;
    }

    public void setDot(int dot) {
        this.dot = dot;
    }

    @Override
    public String toString() {
        return "Item{" +
                "left='" + left + '\'' +
                ", right=" + right +
                ", dot=" + dot +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return dot == item.dot && Objects.equals(left, item.left) && Objects.equals(right, item.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, dot);
    }
}
