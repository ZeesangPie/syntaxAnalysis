package Entity;

import java.util.List;
import java.util.Objects;

public class Product {
    String left;
    List<String> right;

    public Product(String left, List<String> right) {
        this.left = left;
        this.right = right;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(left, product.left) && Objects.equals(right, product.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
