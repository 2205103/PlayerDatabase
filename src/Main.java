import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

    Stage stage;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            this.stage=primaryStage;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fx.fxml"));
            Scene scene=new Scene(loader.load(),600,640);

            // Set up the stage
            primaryStage.setTitle("Cricket Player Database");
            primaryStage.setScene(scene);
            primaryStage.show();


            Control ctrl=loader.getController();
            ctrl.setObject(this);
            //new PlayerServer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void nextScence(Scene newScene)
    {
        stage.setScene(newScene);
        stage.show();
    }
}
