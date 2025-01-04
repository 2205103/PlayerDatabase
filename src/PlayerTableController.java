
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class PlayerTableController {
    private SocketWrapper socketWrapper;
    private LoginController loginController;
    private ReadThread r;
    private String clubname;


    @FXML
    private TableView<Player> playersTable;

    @FXML
    private TableColumn<Player, String> nameColumn;

    @FXML
    private TableColumn<Player, String> countryColumn;

    @FXML
    private TableColumn<Player, Integer> ageColumn;

    @FXML
    private TableColumn<Player, Double> heightColumn;

    @FXML
    private TableColumn<Player, String> clubColumn;

    @FXML
    private TableColumn<Player, String> positionColumn;

    @FXML
    private TableColumn<Player, Integer> numberColumn;

    @FXML
    private TableColumn<Player, Double> salaryColumn;



    void setClubname(String clubname)
    {
        this.clubname=clubname;
    }
    void setSocketWrapper(SocketWrapper socketWrapper)
    {
        this.socketWrapper=socketWrapper;
    }
    public void setController(LoginController loginController) {
        this.loginController=loginController;
    }



    public void setPlayers(List<Player> players) {
        // Map the TableView columns to Player class properties
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        // Add the player data to the TableView
        playersTable.setItems(FXCollections.observableArrayList(players));
    }




    public void BuyPlayer(ActionEvent actionEvent) throws IOException {      //When the user presses "Buy Player" button
        try {
            // Load the FXML file for buying players
            FXMLLoader loader = new FXMLLoader(getClass().getResource("buy_player.fxml"));
            Scene scene = new Scene(loader.load(), 1030, 506);

            // Set up the controller
            BuyController controller = loader.getController();
            controller.setSocket(socketWrapper);
            controller.setClubname(this.clubname);

            if(r==null || !r.isAlive()) {
                r = new ReadThread(socketWrapper, controller.getPlayersTable(),loginController,clubname);
                r.start();
            }
            else
            {
                r.setTableView(controller.getPlayersTable());
            }

            System.out.println(clubname+" Wants to see SALABLE PLAYERS");
            Request msg = new Request("ShowSale");  // Request for the current list of players for sale
            socketWrapper.write(msg);  // Send the request to the server



            // Open a new window (Stage) for the Buy Player screen
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Buy Player for"+clubname);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void SellPlayer(ActionEvent actionEvent){  //When the User presses "Sell Player" button
        System.out.println(clubname+" made a SALE request");
        Player selectedPlayer = playersTable.getSelectionModel().getSelectedItem();
        if (selectedPlayer != null) {
            try {
                Request msg=new Request("sale",selectedPlayer); //making a request for selling players
                socketWrapper.write(msg); //send the selected name to the server
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}