import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Map;

public class CountryWisePlayerController {

    @FXML
    private TableView<CountryPlayerPair> countryTable;

    @FXML
    private TableColumn<CountryPlayerPair, String> countryColumn;

    @FXML
    private TableColumn<CountryPlayerPair, Integer> playerCountColumn;

    public void setCountryPlayerData(List<Map.Entry<String, Integer>> countryPlayerCounts) {
        // Map the TableView columns to the CountryPlayerPair properties
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        playerCountColumn.setCellValueFactory(new PropertyValueFactory<>("playerCount"));

        // Convert the Map.Entry list to a list of CountryPlayerPair objects
        List<CountryPlayerPair> countryPlayerPairs = countryPlayerCounts.stream()
                .map(entry -> new CountryPlayerPair(entry.getKey(), entry.getValue()))
                .toList();

        // Add the data to the TableView
        countryTable.setItems(FXCollections.observableArrayList(countryPlayerPairs));
    }

    // Nested class to represent the pair of country and player count
    public static class CountryPlayerPair {
        private final String country;
        private final Integer playerCount;

        public CountryPlayerPair(String country, Integer playerCount) {
            this.country = country;
            this.playerCount = playerCount;
        }

        public String getCountry() {
            return country;
        }

        public Integer getPlayerCount() {
            return playerCount;
        }
    }
}
