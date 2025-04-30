import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users;

    public UserManager() {
        this.users = new HashMap<>();
    }

    public UserManager(Map<String, User> users) {
        this.users = users;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void registerUser(User userToRegister) {
        if (userToRegister.getEmail() == null || userToRegister.getEmail().isEmpty()
                || userToRegister.getPassword() == null || userToRegister.getPassword().isEmpty()
                || userToRegister.getFirstName() == null || userToRegister.getFirstName().isEmpty()
                || userToRegister.getLastName() == null || userToRegister.getLastName().isEmpty()) {
            throw new InvalidParameterException("All fields must be non-null and non-empty.");
        }

        if (users.containsKey(userToRegister.getEmail())) {
            throw new IllegalStateException("User already registered.");
        }

        users.put(userToRegister.getEmail(), userToRegister);
    }

    public User loginUser(User userToLogin) {
        if (userToLogin.getEmail() == null || userToLogin.getEmail().isEmpty()
                || userToLogin.getPassword() == null || userToLogin.getPassword().isEmpty()) {
            throw new InvalidParameterException("Email and password must be non-null and non-empty.");
        }

        if (!users.containsKey(userToLogin.getEmail())) {
            throw new IllegalArgumentException("User does not exist.");
        }

        User storedUser = users.get(userToLogin.getEmail());
        if (!storedUser.getPassword().equals(userToLogin.getPassword())) {
            throw new IllegalStateException("Incorrect password.");
        }

        return storedUser;
    }

    public static void main(String[] args) {
        UserManager manager = new UserManager();
        User user = new User("test@example.com", "password123", "Jane", "Doe");

        try {
            manager.registerUser(user);
            System.out.println("User registered successfully!");

            User loggedInUser = manager.loginUser(new User("test@example.com", "password123"));
            System.out.println("User logged in successfully as: " + loggedInUser.getFirstName());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
