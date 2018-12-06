package view;

import java.text.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.Event;


public class TableViewController {
	
	private LyricsSearchEngineController lse;
	
	private Event currentEvent;
	
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
 
@SuppressWarnings("deprecation")
public void initializeController(LyricsSearchEngineController lse, Event currentEvent) throws ParseException
  {
	this.lse = lse;
	this.currentEvent = currentEvent;
	
    	titel.setCellValueFactory(new PropertyValueFactory<>("Titel"));
    veroeffentlicht.setCellValueFactory(new PropertyValueFactory<>("Veröffentlicht"));
    album.setCellValueFactory(new PropertyValueFactory<>("Album"));
    	kuenstler.setCellValueFactory(new PropertyValueFactory<>("Künstler"));
    	genre.setCellValueFactory(new PropertyValueFactory<>("Genre"));

    	if (currentEvent != null && currentEvent.getSong() != null)
    		{
    			ObservableList<Song> options = FXCollections.observableArrayList((Song)currentEvent.getSong());
    			songTabelle.setItems(options);
    		}
    }
    
}


