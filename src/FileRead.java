import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileRead {
    static final String FILE_NAME = "players.txt";

    public List<Player> getPlayers() throws IOException {
        List<Player> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] parts = text.split(",");
                Player p = new Player();
                p.setName(parts[0]);
                p.setCountry(parts[1]);
                p.setAge(Integer.parseInt(parts[2]));
                p.setHeight(Double.parseDouble(parts[3]));
                p.setClub(parts[4]);
                p.setPosition(parts[5]);
                p.setNumber(parts[6].isEmpty() ? -1 : Integer.parseInt(parts[6]));
                p.setSalary(Double.parseDouble(parts[7]));
                list.add(p);
            }
        }
        return list;
    }
}
