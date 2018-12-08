package view;

import java.util.ArrayList;

public class SongController{
	
	private LyricsSearchEngineController lse;
	
public void songEintragen(String titel, int veroeffentlicht, String album, String kuenstler, String genre) throws IllegalArgumentException{
	Song song = this.lse.getSong();
	ArrayList<SongInformation> songList = song.getSongInformation();
	SongInformation songinformation = new SongInformation(titel, veroeffentlicht, album, kuenstler, genre);
	
	if(songList == null){
		songList = new ArrayList<SongInformation>();
	}
	songList.add(songinformation);
	song.setEintrag(songList);
}
}
