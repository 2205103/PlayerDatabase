import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Pair;

import java.util.List;

public class ClubWiseSalaryController {

    @FXML
    private TableView<Pair<String, Double>> salaryTable;

    @FXML
    private TableColumn<Pair<String, Double>, String> clubColumn;

    @FXML
    private TableColumn<Pair<String, Double>, Double> yearlySalaryColumn;

    /**
     * Initialize method called after FXML is loaded.
     * This maps the TableView columns to the Pair properties.
     */
    @FXML
    public void initialize() {
        // Map the clubColumn to the Pair's key (club name)
        clubColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));

        // Map the yearlySalaryColumn to the Pair's value (yearly salary)
        yearlySalaryColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getValue()));
    }

    /**
     * Sets the player salary data into the TableView.
     * @param sampleData A list of Pair<String, Double> where:
     *                   - Pair.first is the player's club name
     *                   - Pair.second is the yearly salary
     */
    public void setCountryPlayerData(List<Pair<String, Double>> sampleData) {
        // Populate the TableView with the provided sample data
        salaryTable.setItems(FXCollections.observableArrayList(sampleData));
    }
}
