import java.util.HashMap;
import java.util.Map;

public class CartManager {
    private Map<String, Map<CartItem, Integer>> userCarts;

    public CartManager() {
        this.userCarts = new HashMap<>();
    }

    public CartManager(Map<String, Map<CartItem, Integer>> userCarts) {
        this.userCarts = userCarts;
    }

    public Map<String, Map<CartItem, Integer>> getUserCarts() {
        return userCarts;
    }

    public Map<CartItem, Integer> getUserCart(String email) {
        return userCarts.getOrDefault(email, new HashMap<>());
    }

    public void addToCart(String email, CartItem item) {
        userCarts.putIfAbsent(email, new HashMap<>());
        Map<CartItem, Integer> cart = userCarts.get(email);
        cart.put(item, cart.getOrDefault(item, 0) + 1);
    }

    public static void main(String[] args) {
        CartManager cartManager = new CartManager();
        CartItem apple = new CartItem("apple.png", "Apple", 5);

        String email = "test@example.com";
        cartManager.addToCart(email, apple);
        cartManager.addToCart(email, apple);

        Map<CartItem, Integer> userCart = cartManager.getUserCart(email);
        userCart.forEach((item, quantity) ->
                System.out.println(item.getName() + ": " + quantity + " pcs"));
    }
}

