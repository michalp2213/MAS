import Control.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private final int resolutionX = 800;
    private final int resolutionY = 600;

    public static void main( String[] args ) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/GUI.fxml"));
        Parent root = loader.load();
        Scene mainScene = new Scene(root, resolutionX, resolutionY);
        mainScene.getStylesheets().add(App.class.getResource("View/Style.css").toExternalForm());
        primaryStage.setTitle("MAS");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}