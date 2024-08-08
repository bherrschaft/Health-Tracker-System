import java.util.HashMap; // Import HashMap class
import java.util.Map; // Import Map interface

public class UserManager { // Declare a class named UserManager
    private Map<String, User> users = new HashMap<>(); // Create a map to store users with their usernames as keys

    public void createUser(String username) { // Method to create a new user
        if (!users.containsKey(username)) { // Check if the username does not already exist
            users.put(username, new User(username)); // Add the new user to the map
            System.out.println("User created successfully."); // Print success message
        } else {
            System.out.println("Username already exists."); // Print error message if username exists
        }
    }

    public User loginUser(String username) { // Method to log in a user
        if (users.containsKey(username)) { // Check if the username exists
            System.out.println("Login successful."); // Print success message
            return users.get(username); // Return the User object
        } else {
            System.out.println("User not found."); // Print error message if user not found
            return null; // Return null if user does not exist
        }
    }
}
