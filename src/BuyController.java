
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BuyController {
    @FXML private TableView<Player> playersTable;
    @FXML private Button buyButton;
    private String clubname;

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    private SocketWrapper socketWrapper;

    public void setSocket(SocketWrapper socketWrapper) {
        this.socketWrapper=socketWrapper;
    }

    @FXML
    private void initialize() {
        buyButton.setOnAction(event -> {
            Player selectedPlayer = playersTable.getSelectionModel().getSelectedItem();
            if (selectedPlayer != null) {
                try {
                    Request msg=new Request("buy",selectedPlayer,clubname);
                    socketWrapper.write(msg);   //send the selected name to the server
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TableView<Player> getPlayersTable() {
        return playersTable;
    }
}
