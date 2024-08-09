import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HealthTrackerApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create UI components
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Button loginButton = new Button("Log In");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            if (username.isEmpty()) {
                System.out.println("Please enter a username.");
            } else {
                // Handle login logic
                System.out.println("Logged in as: " + username);
            }
        });

        // Add components to the grid
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(loginButton, 1, 1);

        // Create and set the scene
        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setTitle("Health and Wellness Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
