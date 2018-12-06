package view;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class LyricsSearchEngineController {
	
	private LyricsSearchEngineController lse;
	
	
	public LyricsSearchEngineController(LyricsSearchEngineController lse2) {
		lse = this.lse;
	}


	@FXML
    void suchenAction(ActionEvent event) {
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LyricsSearchEngine.fxml"));

	
	try {
		fxmlLoader.load();
		LyricsSearchEngineController lse = (LyricsSearchEngineController) fxmlLoader.getController();
		
		Stage stage = new Stage();
		//stage = (Stage) suchen.getScene().getWindow();
		Scene scene = new Scene(fxmlLoader.getRoot());
		stage.setScene(scene);
	} catch (Exception e) {
	    throw new RuntimeException(e);
}
	}
	
	
public void programmSpeichern() {
		 
		
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream("LyricsEngineSave");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(lse);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in LyricsSearchEngine as LyricsSearchEngineSave");
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
	}
		
    }

