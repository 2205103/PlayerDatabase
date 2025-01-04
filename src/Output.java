import javafx.scene.control.Label;

public class Output {


    public Label playerNameLabel;
    public Label positionLabel;
    public Label ageLabel;
    public Label salaryLabel;
    public Label countryLabel;
    public Label heightLabel;
    public Label weightLabel;

    public void setPlayerData(Player player) {
        playerNameLabel.setText(player.getName());
        positionLabel.setText(player.getPosition());
        ageLabel.setText(String.valueOf(player.getAge()));
        salaryLabel.setText(String.valueOf(player.getSalary()));
        countryLabel.setText(player.getCountry());
        heightLabel.setText(String.valueOf(player.getHeight()));
        weightLabel.setText(String.valueOf(player.getClub()));
    }
}
