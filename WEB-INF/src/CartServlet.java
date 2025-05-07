import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CartManager cartManager;
    private DatabaseManager db;

    @Override
    public void init() throws ServletException {
        db = new DatabaseManager();
        cartManager = new CartManager(db.getUserCarts());
    }

    @Override
    public void destroy() {
        db.writeUserCarts(cartManager.getUserCarts());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/catalog/login.html");
            return;
        }

        String email = (String) session.getAttribute("email");
        if (email == null) {
            session.invalidate();
            response.sendRedirect("/catalog/login.html");
            return;
        }

        Map<CartItem, Integer> userCart = cartManager.getUserCart(email);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (userCart == null || userCart.isEmpty()) {
            out.println("<html><body>");
            out.println("<h2>Your cart is empty!</h2>");
            out.println("<a href='/catalog/catalog.html'>Back to Catalog</a>");
            out.println("</body></html>");
        } else {
            String html = CartSummaryHtmlGenerator.getSummaryPage(userCart);
            out.println(html);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/catalog/login.html");
            return;
        }

        String email = (String) session.getAttribute("email");
        if (email == null) {
            session.invalidate();
            response.sendRedirect("/catalog/login.html");
            return;
        }

        String imgAddress = request.getParameter("imgAddress");
        String itemName = request.getParameter("itemName");
        String itemPriceStr = request.getParameter("itemPrice");

        try {
            int itemPrice = Integer.parseInt(itemPriceStr);
            CartItem cartItem = new CartItem(imgAddress, itemName, itemPrice);
            cartManager.addToCart(email, cartItem);
            response.sendRedirect("/catalog/catalog.html");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid item price.");
        }
    }
}
