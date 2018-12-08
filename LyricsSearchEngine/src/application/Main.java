package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.AudioParser;
import view.DatenbankSongs;
import view.LyricsSearchEngineController;




public class Main extends Application {
	
	private LyricsSearchEngineController lse;
	
	@Override
	public void start(Stage primaryStage) {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LyricsSearchEngine.fxml"));
		Parent root = new GridPane();

		try {
			root = fxmlLoader.load();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		try {
    	    
			  FileInputStream fileIn = new FileInputStream("LyricsSearchEngineSave");
			  ObjectInputStream in = new ObjectInputStream(fileIn);
			  lse = (LyricsSearchEngineController) in.readObject();
			  in.close();
			  fileIn.close();
		      	} catch (IOException i) {
		      		lse = null;
		      	} catch (ClassNotFoundException c) {
		      		System.out.println("LyricsSearchEngine class not found");
		      		c.printStackTrace();
		      		return;
		      	}
			LyricsSearchEngineController controller = new LyricsSearchEngineController(lse);
		
		LyricsSearchEngineController sc = (LyricsSearchEngineController) fxmlLoader.getController();
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(x -> controller.programmSpeichern());
		primaryStage.show();
		
	}

	      
	
	public static void main(String[] args){
		//System.out.println(getClass().getResource("/view/LyricsSearchEngine.fxml"));
		try {
		//DatenbankSongs.Datenbank();
			AudioParser.metaInformation();
		}
		catch(Exception e) {
			System.out.println("Fehler!");
		}
		
		launch(args);
	}
}
