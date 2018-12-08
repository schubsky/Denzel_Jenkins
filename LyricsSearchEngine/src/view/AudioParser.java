package view;

import java.io.FileNotFoundException;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

public class AudioParser {

		public static void metaInformation(){
	try {
	Mp3File mp3file = new Mp3File(DatenbankSongs.Datenbank().get(2));
	

	if (mp3file.hasId3v2Tag()) {
	  ID3v2 id3v2Tag = mp3file.getId3v2Tag();
	  System.out.println("Title: " + id3v2Tag.getTitle());
	  System.out.println("Year: " + id3v2Tag.getYear());
	  System.out.println("Album: " + id3v2Tag.getAlbum());
	  System.out.println("Artist: " + id3v2Tag.getArtist());
	  System.out.println("Genre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
	  System.out.println("Lyrics: " + id3v2Tag.getLyrics());
	  System.out.println(mp3file);;
	  byte[] albumImageData = id3v2Tag.getAlbumImage();
	  if (albumImageData != null) {
	    System.out.println("Have album image data, length: " + albumImageData.length + " bytes");
	    System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());
	  }
	}
	}
	catch(Exception e){
		 System.out.println("Fehlerhafte MP3");
	}
}

		}

