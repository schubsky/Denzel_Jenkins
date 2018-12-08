package view;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class DatenbankSongs {

	public static ArrayList<String> Datenbank() throws FileNotFoundException
	{
		    String music00 = "/Users/minhkhoavu/eclipse-workspace/LyricsSearchEngine/src/resources/music/01 Saint Cecilia.mp3";
		    String music01 = "/Users/minhkhoavu/eclipse-workspace/LyricsSearchEngine/src/resources/music/02 Sean.mp3";
		    String music02 = "/Users/minhkhoavu/eclipse-workspace/LyricsSearchEngine/src/resources/music/03 Savior Breath.mp3";
		    String music03 = "/Users/minhkhoavu/eclipse-workspace/LyricsSearchEngine/src/resources/music/04 Iron Rooster.mp3";
		    String music04 = "/Users/minhkhoavu/eclipse-workspace/LyricsSearchEngine/src/resources/music/05 The Neverending Sigh.mp3";

			ArrayList<String> Datenbank = new ArrayList<String>();
			Datenbank.add(music00);
			Datenbank.add(music01);
			Datenbank.add(music02);
			Datenbank.add(music03);
			Datenbank.add(music04);
			System.out.println(Datenbank.toString());
			System.out.println("Hello World!");
			return Datenbank;
	}
	
	/*public FileReader getSong(ArrayList<FileReader> Datenbank, int index) {
		for(int i = 0; i<=index; i++)
		{
		}
		return Datenbank.get(index);
	}*/
}

