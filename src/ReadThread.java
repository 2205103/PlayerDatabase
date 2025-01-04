
/*
Only ReadThread for each Club
* */

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ReadThread extends Thread {

    private LoginController loginController;
    //private volatile boolean running = true; // Flag to control the thread
    private TableView<Player> tableView;
    private SocketWrapper socketWrapper;
    String clubname;

    public ReadThread(SocketWrapper socketWrapper, TableView<Player> tableView,LoginController loginController,String clubname) {
        this.socketWrapper = socketWrapper;
        this.loginController=loginController;
        this.tableView = tableView;
        this.clubname=clubname;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Read the list of players from the server
                Object o = socketWrapper.read();

                if( "MainList".equalsIgnoreCase(((Request) o).getTag()))  //It means the server has sent the clubs ALL PLAYER LIST
                {
                    System.out.print("Server sends ALL PLAYER LIST to ");
                    Platform.runLater(() -> {
                        try {
                            loginController.setScene(((Request) o).getPlayerList());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                else {
                    System.out.println("Server sends SALABLE PLAYERS LIST"); //It means the Server has sent SALABLE PLAYERS list
                    synchronized (o) {
                        ObservableList<Player> playerList = FXCollections.observableArrayList(filter(((Request) o).getPlayerList()));

                        Platform.runLater(() -> {
                            // Clear the table temporarily to force a refresh
                            tableView.getItems().clear();

                            // Assign the updated list
                            tableView.setItems(playerList);

                            // Force the table to refresh (if clearing doesn't work alone)
                            tableView.refresh();
                        });
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to stop the thread safely
    public void setTableView(TableView<Player> tableView) {
        this.tableView=tableView;
    }

    private List<Player> filter(List<Player> playerList)
    {
        List<Player> result = new ArrayList<>();
        // Iterate through the values of the map
        synchronized (playerList) {
            for (Player player : playerList) {
                if (!player.getClub().equalsIgnoreCase(clubname)) {
                    result.add(player);
                }
            }
        }
        return result;
    }
}
