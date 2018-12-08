package view;

import java.io.Serializable;
import java.util.ArrayList;


public class SongInformation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -811586991242750594L;

	private String titel;

	private int veroeffentlicht;

	private String album;

	private String kuenstler;

	private String genre;
	
	private ArrayList<Object> song;
	
	public SongInformation(String titel, int veroeffentlicht, String album, String kuenstler, String genre){
		this.titel = titel;
		this.veroeffentlicht = veroeffentlicht;
		this.album = album;
		this.kuenstler = kuenstler;
		this.genre = genre;
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
	
	public String getKuenstler() {
		return kuenstler;
	}

	public void setKuenstler(String kuenstler) {
		this.kuenstler = kuenstler;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
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