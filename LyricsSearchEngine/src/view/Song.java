package view;

import java.io.Serializable;
import java.util.ArrayList;

public class Song implements Serializable{


	private String titel;

	private int veroeffentlicht;

	private String album;

	private String kuenstler;

	private String genre;
	
	private ArrayList<Object> song;
	
	public Song(){
		song = new ArrayList<Object>();
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public int getVeroeffentlicht() {
		return veroeffentlicht;
	}

	public void setVeroeffentlicht(int veroeffentlicht) {
		this.veroeffentlicht = veroeffentlicht;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public ArrayList<Object> getSong() {
		return song;
	}

	public void setPraeferenzen(ArrayList<Object> song) {
		this.song = song;
	}

	public void addSong(String string) {
		song.add(string);
	}
	
}
