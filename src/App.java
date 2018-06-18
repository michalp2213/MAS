import java.io.IOException;

import Control.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import Model.Database;

public class App extends Application {
    private final int resolutionX = 1300;
    private final int resolutionY = 600;

    public static void main( String[] args ) {
    	Database.setServerAddr("jdbc:postgresql://localhost:5432/mas"); // set this up for your db config
    	try {
			Database.connect("psimaj", "testpass");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1); // cannot work without DB connection
		}
    	
        launch(args);
        
        Database.disconnect();
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