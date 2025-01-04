import java.io.Serializable;
import java.util.List;

class Request extends MainList implements Serializable{
    private String requestType;
    private Player player;
    private String buyerClub;

    public Request(List<Player> playerList)
    {
        super(playerList);
    }

    public Request(List<Player>playerList,int i)
    {
        super(playerList);
        changeTag();
    }

    public Request(String requestType, Player player) {
        this.requestType = requestType;
        this.player = player;
        changeTag();
    }
    public Request(String requestType) {
        this.requestType = "ShowSale";
        this.player=null;
        changeTag();
    }
    public Request(String requestType, Player player,String buyerClub) {
        this.requestType = requestType;
        this.player = player;
        this.buyerClub=buyerClub;
        changeTag();
    }

    public String getBuyerClub() {
        return buyerClub;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }

    public Player getPlayer() {
        return player;
    }
    public String getTag()
    {
        return this.tag;
    }

    @Override
    public String toString() {
        return "Request{requestType='" + requestType + "', player=" + player + "}";
    }

    void changeTag() {
        this.tag="Request";
    }
}