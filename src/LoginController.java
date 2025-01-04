
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class LoginController {

    // HashMap to store club names and their passwords
    private HashMap<String, String> managers = new HashMap<>();
    private List<Player>list;
    void setStage(Stage stage)
    {
        this.stage=stage;
    }
    void setManagers(HashMap hashmap)
    {
        this.managers=hashmap;
    }
    SocketWrapper socketWrapper;
    String clubName;
    @FXML
    private Stage stage;

    @FXML
    private TextField clubNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField reenterPasswordField;

    @FXML
    private Label statusLabel;

    @FXML
    public void handleRegister() { //SignUp
        this.clubName = clubNameField.getText().trim();
        String password = passwordField.getText();
        String reenteredPassword = reenterPasswordField.getText();

        if (clubName.isEmpty() || password.isEmpty() || reenteredPassword.isEmpty()) {
            statusLabel.setText("Please fill out all fields.");
            return;
        }

        if (managers.containsKey(clubName)) {
            statusLabel.setText("Already exists, please login.");
        } else {
            if (!password.equals(reenteredPassword)) {
                statusLabel.setText("Passwords did not match, re-enter.");
            } else {
                managers.put(clubName, password);
                statusLabel.setText("Club registered successfully!");
                WriteToFile(managers);
            }
        }
    }


    public void handleLogin() throws Exception {  //Whenever a user login
        statusLabel.setText("");
        this.clubName = clubNameField.getText().trim();
        String password = passwordField.getText();

        if (clubName.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please fill out all fields.");
            return;
        }

        if (!managers.containsKey(clubName)) {
            statusLabel.setText("Club not Found! Please Register");
        } else {
            if (!Objects.equals(managers.get(clubName), password)) {
                statusLabel.setText("Passwords did not match, re-enter.");
            }



            else {//Connecting to Server

                String serverAddress = "127.0.0.1";
                int serverPort = 33333;
                this.socketWrapper = new SocketWrapper(serverAddress, serverPort);
                socketWrapper.write(clubName);
                List<Player>temp=((Request) socketWrapper.read()).getPlayerList();



                    // Load the FXML file with TableView
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("BuySell.fxml"));
                    Scene scene = new Scene(loader.load(), 937, 506);

                    // Get the controller and pass the data
                    PlayerTableController controller = loader.getController();
                    controller.setPlayers(searchByClub(temp));// Pass the list of players to the controller

                    controller.setSocketWrapper(socketWrapper);
                    controller.setClubname(clubName);
                    controller.setController(this);
                    // Create a new stage and set the scene
                    this.stage = new Stage();
                    stage.setTitle("Search Results "+clubName);
                    stage.setScene(scene);
                    stage.show();
            }
        }

    }



    public void setScene(List<Player>temp) throws IOException { //This Method Is Called when the Server sends ALL PLAYERS LIST
        System.out.println(clubName);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BuySell.fxml"));
        Scene scene = new Scene(loader.load(), 937, 506);
        PlayerTableController controller = loader.getController();
        controller.setPlayers(searchByClub(temp));// Pass the list of players to the controller
        controller.setSocketWrapper(socketWrapper);
        controller.setClubname(clubName);
        controller.setController(this);
        // Set the Scene to the Current Stage

        stage.setTitle("Search Results"+clubName);
        stage.setScene(scene);
    }




    public void WriteToFile(HashMap<String, String> managers) {  //Handling Login and SignUP


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("managers.txt"))) {
            for (String club : managers.keySet()) {
                String password = managers.get(club);
                writer.write(club + ":" + password);
                writer.newLine();
            }
        } catch (IOException e) {

        }
    }




    List<Player> searchByClub(List<Player>newPlayerList) {
        List<Player> result = new ArrayList<>();
        // Iterate through the values of the map
        synchronized (newPlayerList) {
            for (Player player : newPlayerList) {
                if (player.getClub().equalsIgnoreCase(clubName)) {
                    result.add(player);
                }
            }
        }
        return result;
    }
}
