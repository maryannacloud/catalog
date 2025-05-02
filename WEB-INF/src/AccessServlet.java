import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.InvalidParameterException;

public class AccessServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserManager userManager;
    private DatabaseManager db;

    @Override
    public void init() throws ServletException {
        db = new DatabaseManager();
        userManager = new UserManager(db.getUsers());
    }

    @Override
    public void destroy() {
        db.writeUsers(userManager.getUsers());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action parameter.");
            return;
        }

        switch (action) {
            case "login":
                loginAction(request, response);
                break;
            case "register":
                registerAction(request, response);
                break;
            case "logout":
                logoutAction(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action.");
        }
    }

    private void loginAction(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Check for existing session
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object existingEmail = session.getAttribute("email");
            if (existingEmail == null) {
                session.invalidate();
                response.sendRedirect("/catalog/login.html");
                return;
            }

            if (existingEmail.equals(email)) {
                response.sendRedirect("/catalog/catalog.html");
                return;
            } else {
                session.invalidate();
                response.sendRedirect("/catalog/login.html");
                return;
            }
        }

        try {
            User userToLogin = new User(email, password);
            User loggedInUser = userManager.loginUser(userToLogin);

            // Create new session
            session = request.getSession(true);
            session.setAttribute("email", loggedInUser.getEmail());

            response.sendRedirect("/catalog/catalog.html");
        } catch (Exception e) {
            String errorResponse = "Login failed: " + e.getMessage();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorResponse);
        }
    }

    private void registerAction(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        // Prevent registration if user is already signed in
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Please log out before registering.");
            return;
        }

        try {
            User registeringUser = new User(email, password, firstName, lastName);
            userManager.registerUser(registeringUser);
            response.sendRedirect("/catalog/login.html");
        } catch (InvalidParameterException | IllegalStateException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Registration failed: " + e.getMessage());
        }
    }

    private void logoutAction(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect("/catalog/login.html");
    }
}
