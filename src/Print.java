import java.util.List;

public class Print {
    public static void show(List<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            System.out.println(p.getName());
        }
    }
}
