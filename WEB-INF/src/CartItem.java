import java.io.Serializable;
import java.util.Objects;

public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private String imgAddress;
    private String name;
    private int price;

    public CartItem(String imgAddress, String name, int price) {
        this.imgAddress = imgAddress;
        this.name = name;
        this.price = price;
    }

    public String getImgAddress() { return imgAddress; }
    public void setImgAddress(String imgAddress) { this.imgAddress = imgAddress; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(name, cartItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static void main(String[] args) {
        CartItem item1 = new CartItem("img1.png", "Apple", 5);
        CartItem item2 = new CartItem("img2.png", "Apple", 7);
        CartItem item3 = new CartItem("img3.png", "Banana", 3);

        System.out.println(item1.equals(item2)); // true (same name)
        System.out.println(item1.equals(item3)); // false
        System.out.println(item1.hashCode() == item2.hashCode()); // true
    }
}
