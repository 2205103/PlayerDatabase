import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerList extends FileRead {
    List<Player> player_list = new ArrayList<>();
    public static List<Player> newPlayer = new ArrayList<>();

    public PlayerList() {
        try {
            this.player_list = getPlayers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void add(Player p)
    {
        this.player_list.add(p);
        newPlayer.add(p);
    }
    public void AddtoFile() throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            for (Player player : newPlayer) {
                String line = String.format("\n%s,%s,%d,%.2f,%s,%s,%d,%.2f",
                        player.getName(), player.getCountry(), player.getAge(), player.getHeight(),
                        player.getClub(), player.getPosition(),
                        player.getNumber() != -1 ? player.getNumber() : 0, player.getSalary());
                writer.write(line);
            }
        } catch (IOException e) {
            throw new IOException("Error writing to the file", e);
        }
    }
}
