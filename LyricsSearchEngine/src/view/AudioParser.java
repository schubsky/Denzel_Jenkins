package view;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

public class AudioParser {

	Mp3File mp3file = new Mp3File("01 Saint Cecilia.mp3");{
	if (mp3file.hasId3v2Tag()) {
	  ID3v2 id3v2Tag = mp3file.getId3v2Tag();
	  System.out.println("Track: " + id3v2Tag.getTrack());
	  System.out.println("Artist: " + id3v2Tag.getArtist());
	  System.out.println("Title: " + id3v2Tag.getTitle());
	  System.out.println("Album: " + id3v2Tag.getAlbum());
	  System.out.println("Year: " + id3v2Tag.getYear());
	  System.out.println("Genre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
	  System.out.println("Comment: " + id3v2Tag.getComment());
	  System.out.println("Lyrics: " + id3v2Tag.getLyrics());
	  System.out.println("Composer: " + id3v2Tag.getComposer());
	  System.out.println("Publisher: " + id3v2Tag.getPublisher());
	  System.out.println("Original artist: " + id3v2Tag.getOriginalArtist());
	  System.out.println("Album artist: " + id3v2Tag.getAlbumArtist());
	  System.out.println("Copyright: " + id3v2Tag.getCopyright());
	  System.out.println("URL: " + id3v2Tag.getUrl());
	  System.out.println("Encoder: " + id3v2Tag.getEncoder());
	  byte[] albumImageData = id3v2Tag.getAlbumImage();
	  if (albumImageData != null) {
	    System.out.println("Have album image data, length: " + albumImageData.length + " bytes");
	    System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());
	  }
	}
}
}
