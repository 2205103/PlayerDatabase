import javafx.util.Pair;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerStats {

    public static List<Player> getMaxSalaryPlayers(List<Player> temp) {
        double maxSalary = Double.MIN_VALUE;
        for (Player p : temp) {
            if (p.getSalary() > maxSalary) {
                maxSalary = p.getSalary();
            }
        }

        List<Player> maxSalaryPlayers = new ArrayList<>();
        for (Player p : temp) {
            if (p.getSalary() == maxSalary) {
                maxSalaryPlayers.add(p);
            }
        }

        return maxSalaryPlayers;
    }

    public static List<Player> getMaxAgePlayers(List<Player> temp) {
        int maxAge = Integer.MIN_VALUE;
        for (Player p : temp) {
            if (p.getAge() > maxAge) {
                maxAge = p.getAge();
            }
        }

        List<Player> maxAgePlayers = new ArrayList<>();
        for (Player p : temp) {
            if (p.getAge() == maxAge) {
                maxAgePlayers.add(p);
            }
        }

        return maxAgePlayers;
    }

    public static List<Player> getMaxHeightPlayers(List<Player> temp) {
        double maxHeight = Double.MIN_VALUE;
        for (Player p : temp) {
            if (p.getHeight() > maxHeight) {
                maxHeight = p.getHeight();
            }
        }

        List<Player> maxHeightPlayers = new ArrayList<>();
        for (Player p : temp) {
            if (p.getHeight() == maxHeight) {
                maxHeightPlayers.add(p);
            }
        }

        return maxHeightPlayers;
    }

    public static List<Pair<String, Double>> getTotalYearlySalary(List<Player> temp) {
        List<Pair<String, Double>> result = new ArrayList<>();
        double yearlySalary=0;
        for (Player p : temp) {
            yearlySalary += p.getSalary();
        }
        result.add(new Pair<>(temp.get(0).getClub(), yearlySalary*52));
        return result;
    }


    public static List<Map.Entry<String, Integer>> countryWisePlayerCount(List<Player> players) {
        List<String> processedCountries = new ArrayList<>();
        List<Map.Entry<String, Integer>> result = new ArrayList<>();

        for (Player p : players) {
            String country = p.getCountry();
            if (processedCountries.contains(country)) {
                continue;
            }
            int count = 0;
            for (Player temp : players) {
                if (temp.getCountry().equalsIgnoreCase(country)) {
                    count++;
                }
            }
            result.add(new AbstractMap.SimpleEntry<>(country, count));
            processedCountries.add(country);
        }

        return result;
    }

}
