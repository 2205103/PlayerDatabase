
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Control extends CreatePlayer{

    public AnchorPane inputform;
    Search DBS=new Search();
    LoginPage loginPage=new LoginPage();
    int SearchOption;

    // FXML elements
    @FXML
    private ChoiceBox<String> mainChoiceBox; // First ChoiceBox
    @FXML
    private ChoiceBox<String> secondaryChoiceBox; // Second ChoiceBox
    @FXML
    private Label EnterBox; // Label for dynamic text display
    @FXML
    private TextField searchField; // TextField for search input (hidden by default)

    // Reference to the main application (used for transitioning scenes)
    private Main object;

    // Setter for injecting the main class
    public void setObject(Main object) {
        this.object = object;
    }

    /**
     * Sets the label text dynamically.
     *
     * @param text The new text for the label.
     */
    public void setLabelText(String text) {
        if (EnterBox != null) {
            EnterBox.setText(text);
        }
    }


    public void ButtonPressed(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("choice.fxml"));
            Scene scene=new Scene(loader.load(),600,640);
            object.nextScence(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Handles the user's selection from the main `ChoiceBox`.
     */
    public void handleChoiceSelection(ActionEvent event) throws IOException {
        // Get the selected option from the first ChoiceBox
        String selectedOption = mainChoiceBox.getSelectionModel().getSelectedItem();

        if (selectedOption != null) {
            switch (selectedOption) {
                case "Search Players" -> handleSearchPlayers();
                case "Search Clubs" -> handleSearchClubs();
                case "Add Player" -> handleAddPlayer();
                case "Exit System" -> handleExitSystem();
                default -> resetToInitialState();
            }
        }
    }

    /**
     * Handles logic for "Search Players".
     */
    private void handleSearchPlayers() {
        // Enable the search field

        // Disable the main `ChoiceBox` and populate the second `ChoiceBox`
        inputform.setVisible(false);
        mainChoiceBox.setDisable(true);
        secondaryChoiceBox.setItems(FXCollections.observableArrayList(
                "By Player Name",
                "By Club and Country",
                "By Position",
                "By Salary Range",
                "Country-wise player count",
                "Back to Main Menu"
        ));

        System.out.println("Search Players selected");

        // Set label and handle second `ChoiceBox` selection
        secondaryChoiceBox.setOnAction(e -> {
            String secondOption = secondaryChoiceBox.getSelectionModel().getSelectedItem();
            if (secondOption != null) {
                searchField.setVisible(true);
                searchField.setText("");
                switch (secondOption) {
                    case "By Player Name" -> {
                        setLabelText("Search by Player Name");
                        SearchOption=1;
                    }
                    case "By Club and Country" -> {
                        SearchOption=2;
                        setLabelText("Search by Country and Club, Separated by comma (NO SPACE)");
                    }
                    case "By Position" ->
                            {
                                setLabelText("Search by Position");
                                SearchOption=3;
                            }
                    case "By Salary Range" -> {
                        SearchOption=4;
                        setLabelText("Search by Salary Range, Separated by comma (NO SPACE)");
                    }
                    case "Country-wise player count" -> {
                        {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("countrywise.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(loader.load(), 937, 506);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }

                            CountryWisePlayerController countryWisePlayerController=loader.getController();
                            List<Map.Entry<String, Integer>> sampleData=PlayerStats.countryWisePlayerCount(DBS.player_list);
                            countryWisePlayerController.setCountryPlayerData(sampleData);

                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                        }
                        setLabelText("Display Country-wise Player Count");
                        res();
                    }
                    case "Back to Main Menu" -> {
                        setLabelText("Returning to Main Menu...");
                        resetToInitialState();
                    }
                }
            }
        });
    }

    /**
     * Handles logic for "Search Clubs".
     */
    private void handleSearchClubs() {
        // Hide the search field (not needed for "Search Clubs")
        inputform.setVisible(false);

        // Disable the main `ChoiceBox` and populate the second `ChoiceBox`
        mainChoiceBox.setDisable(true);
        secondaryChoiceBox.setItems(FXCollections.observableArrayList(
                "Player(s) with the maximum salary of a club",
                "Player(s) with the maximum age of a club",
                "Player(s) with the maximum height of a club",
                "Total yearly salary of a club",
                "Back to Main Menu"
        ));

        System.out.println("Search Clubs selected");

        // Set label and handle second `ChoiceBox` selection
        secondaryChoiceBox.setOnAction(e -> {
            String secondOption = secondaryChoiceBox.getSelectionModel().getSelectedItem();
            if (secondOption != null) {
                searchField.setVisible(true);
                searchField.setText("");
                switch (secondOption) {
                    case "Player(s) with the maximum salary of a club" -> {
                        SearchOption=6;
                        setLabelText("Maximum salary of a club, Enter Club Name");
                    }
                    case "Player(s) with the maximum age of a club" -> {
                        SearchOption=7;
                        setLabelText("Maximum age of a club, Enter Club Name");
                    }
                    case "Player(s) with the maximum height of a club" -> {
                        SearchOption=8;
                        setLabelText("Maximum height of a club, Enter Club Name");
                    }
                    case "Total yearly salary of a club" -> {
                        SearchOption=9;
                        setLabelText("Total yearly salary of a club, Enter Club Name");
                    }
                    case "Back to Main Menu" -> {
                        setLabelText("Returning to Main Menu...");
                        resetToInitialState();
                    }
                }
            }
        });
    }

    /**
     * Handles logic for "Add Player".
     */
    private void handleAddPlayer() throws IOException {
        // Enable the search field (to allow adding a new player)
        searchField.setVisible(false);

        // Set up the stage
        inputform.setVisible(true);
        // Clear the second `ChoiceBox` and update the label
        setLabelText("Add a New Player");
        secondaryChoiceBox.setItems(FXCollections.observableArrayList());

        System.out.println("Add Player selected");
    }

    /**
     * Handles logic for "Exit System".
     */
    private void handleExitSystem() {
        System.out.println("Exiting the system...");
        try {
            DBS.AddtoFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Platform.exit(); // Exit the application
    }

    /**
     * Resets the UI to its initial state.
     */
    private void resetToInitialState() {
        // Enable the main `ChoiceBox`, reset all fields
        mainChoiceBox.setDisable(false);
        mainChoiceBox.getSelectionModel().clearSelection();
        secondaryChoiceBox.setItems(FXCollections.observableArrayList());
        searchField.setVisible(false); // Hide the search field
        setLabelText("Choose an option from the main menu");
    }


    public void res()
    {
        searchField.setVisible(false);
        setLabelText("Choose an option from the main menu");
        handleSearchPlayers();
    }

    public void res2()
    {
        searchField.setVisible(false);
        setLabelText("Choose an option from the main menu");
        handleSearchClubs();
    }
    /**
     * Handles the search field action (triggered when the user presses Enter in the TextField).
     */
    public void SearchFx(ActionEvent actionEvent) throws IOException{
        String searchQuery = searchField.getText(); // Get the input text
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            String[] str;
            List<Player> temp = List.of();
            int flag=0;
            switch (SearchOption) {
                case 1:
                    temp=DBS.SearchByName(searchQuery);
                    res();
                    break;

                case 2:
                    str=searchQuery.split(",");
                    temp=DBS.SearchByCountry(str[0]);
                    if(temp==null)
                    {

                    }

                    else
                    {
                        if(!str[1].equalsIgnoreCase("ANY"))
                            temp=DBS.SearchByClub(str[1]);
                    }
                    res();
                    break;

                case 3:
                    temp=DBS.SearchByPosition(searchQuery);
                    Print.show(temp);
                    res();
                    break;

                case 4:
                    str=searchQuery.split(",");
                    double salary1 = Double.parseDouble(str[0]);
                    double salary2=Double.parseDouble(str[1]);

                    temp=DBS.SearchBySalary(salary1,salary2);
                    res();

                    break;

                case 6:
                    temp = DBS.SearchByClub(searchQuery);
                    temp = PlayerStats.getMaxSalaryPlayers(temp);
                    res2();
                    break;


                case 7:
                    temp = DBS.SearchByClub(searchQuery);
                    temp = PlayerStats.getMaxAgePlayers(temp);
                    res2();

                    break;


                case 8:
                    temp = DBS.SearchByClub(searchQuery);
                    temp = PlayerStats.getMaxHeightPlayers(temp);
                    res2();

                    break;


                case 9:

                    flag=1;
                    temp=DBS.SearchByClub(searchQuery);
                    break;

                default:
                    temp=null;
                    SearchOption=0;
                    break;


            }

            if(flag==0){
                if (temp.size() == 1) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("output.fxml"));
                    Scene scene = new Scene(loader.load(), 600, 640);

                    Output output = loader.getController();
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    output.setPlayerData(temp.get(0));
                    stage.show();
                } else {
                    // Load the FXML file with TableView
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("players_table.fxml"));
                    Scene scene = new Scene(loader.load(), 937, 506);

                    // Get the controller and pass the data
                    PlayerTableController controller = loader.getController();
                    controller.setPlayers(temp); // Pass the list of players to the controller

                    // Create a new stage and set the scene
                    Stage stage = new Stage();
                    stage.setTitle("Search Results"); // Optional: Set the title for the new stage
                    stage.setScene(scene); // Set the newly created scene
                    stage.show(); // Show the new stage
                }
            }
            else
            {
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("yearly.fxml"));
                    Scene scene = null;
                    try {
                        scene = new Scene(loader.load(), 937, 506);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    ClubWiseSalaryController controller=loader.getController();
                    List<Pair<String, Double>> sampleData=PlayerStats.getTotalYearlySalary(temp);
                    controller.setCountryPlayerData(sampleData);

                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }
                setLabelText("Yearly salary of a club");
                res2();
            }
        }

        else {
            setLabelText("Please enter a valid search query.");
        }
    }

    @FXML
    public void handleSubmit() throws IOException {

        // Process the data (e.g., save to database, display confirmation, etc.)
        Player p = Get(DBS.player_list);
        if (p != null) {
            DBS.add(p);
        }
        inputform.setVisible(false);
        nameField.setText("");countryField.setText(""); ageField.setText("");
        heightField.setText("");clubField.setText(""); positionField.setText(" ");
        numberField.setText(""); salaryField.setText("");
        resetToInitialState();
    }

    public void Login(ActionEvent actionEvent) throws IOException {
        loginPage.Login();
    }

    public void SignUp(ActionEvent actionEvent) throws IOException{
        loginPage.SignUp();
    }
}