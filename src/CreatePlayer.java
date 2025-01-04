import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Scanner;

public class CreatePlayer {
    @FXML
    public TextField nameField;
    @FXML
    public TextField countryField;
    @FXML
    public TextField ageField;
    @FXML
    public TextField heightField;
    @FXML
    public TextField clubField;
    @FXML
    public ChoiceBox<String> positionChoiceBox;
    @FXML
    public TextField numberField;
    @FXML
    public TextField salaryField;
    public TextField positionField;


    public Player Get(List<Player> players) {
        Scanner sc = new Scanner(System.in);
        Player p = new Player();

        String name = nameField.getText();

        // Check if player already exists
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(name)) {
                System.out.println("Player already exists.");
                return null;  // Return null if player exists
            }
        }
        p.setName(name);

        // Get player age
        try {
            p.setAge(Integer.parseInt(ageField.getText()));
        } catch (NumberFormatException e) {
            System.out.println("Invalid age input. Please enter a valid integer.");
            return null;
        }

        // Get player club
        p.setClub(clubField.getText());

        p.setCountry(countryField.getText());

        // Get player height
        try {
            p.setHeight(Double.parseDouble(heightField.getText()));
        } catch (NumberFormatException e) {
            System.out.println("Invalid height input. Please enter a valid number.");
            return null;
        }

        // Get player jersey number
        try {
            p.setNumber(Integer.parseInt(numberField.getText()));
        } catch (NumberFormatException e) {
            System.out.println("Invalid jersey number input. Please enter a valid integer.");
            return null;
        }

        // Get player position
        p.setPosition(positionField.getText());

        // Get player salary
        try {
            p.setSalary(Double.parseDouble(salaryField.getText()));
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary input. Please enter a valid number.");
            return null;
        }

        return p;  // Return the new player if all inputs are valid
    }
}
