package view;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



public class LyricsSearchEngineController {
	
	private LyricsSearchEngineController lse;
	
	private Song song;
	
	@FXML
    private AnchorPane TeilnehmerEinladen;
	
    @FXML
    private TableView<Song> songTabelle;
	
    @FXML
    private TableColumn<Song, String> titel;

    @FXML
    private TableColumn<Song, String> veroeffentlicht;

    @FXML
    private TableColumn<Song, String> album;

    @FXML
    private TableColumn<Song, String> kuenstler;
    
    @FXML
    private TableColumn<Song, String> genre;
	
	
	public LyricsSearchEngineController(LyricsSearchEngineController lse) {
		this.lse = lse;
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
	
	@SuppressWarnings("deprecation")
	public void initializeController(LyricsSearchEngineController lse) throws ParseException
    {
    	this.lse = lse;
    	
 		songTabelle.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    	titel.setCellValueFactory(new PropertyValueFactory<>("Titel"));
    	veroeffentlicht.setCellValueFactory(new PropertyValueFactory<>("Veröffentlicht"));
    	album.setCellValueFactory(new PropertyValueFactory<>("Album"));
    	kuenstler.setCellValueFactory(new PropertyValueFactory<>("Künstler"));
    	genre.setCellValueFactory(new PropertyValueFactory<>("Genre"));
    /*	if (currentEvent != null && currentEvent.getTeilnehmer() != null)
    		{
    			ObservableList<Teilnehmer> options = FXCollections.observableArrayList(currentEvent.getTeilnehmer());
    			teilnehmerTabelle.setItems(options);
    		}*/
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
public Song getSong() {
	return song;
}
		
    }

