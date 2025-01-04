
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;

public class LoginPage {
    private HashMap managers;
    Search DBS;
    LoginPage()
    {
        this.managers=ReadFromFile();
    }

    void Login() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("club_login.fxml"));
        Scene scene=new Scene(loader.load(),400,440);

        LoginController loginController=loader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        loginController.setManagers(managers);
        loginController.setStage(stage);
        stage.show();
    }
    void SignUp() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("club_registration.fxml"));
        Scene scene=new Scene(loader.load(),400,440);

        LoginController loginController=loader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        loginController.setManagers(managers);
        stage.show();
    }

    public HashMap<String, String> ReadFromFile() {
        HashMap<String, String> managers = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("managers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) { // Ensure valid key-value pair
                    managers.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return managers;
    }
}
