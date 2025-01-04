import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MainList implements Serializable {
    protected List<Player> playerList;
    protected String tag="MainList";

    MainList()
    {

    }
    MainList(List<Player> playerList)
    {
        this.playerList = playerList;
    }
    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }
}
