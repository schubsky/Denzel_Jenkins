package view;

import java.io.Serializable;
import java.util.ArrayList;


public class Song implements Serializable{

	private static final long serialVersionUID = 1632966711670215017L;

	private ArrayList<SongInformation> songinformation;
	
	public ArrayList<SongInformation> getSongInformation() {
		return songinformation;
	}

	public void setEintrag(ArrayList<SongInformation> songinformation) {
		this.songinformation = songinformation;
	}


}
