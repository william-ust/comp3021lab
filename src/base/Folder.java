package base;

import java.util.ArrayList;

public class Folder {
	
	private ArrayList<Note> notes;
	private String name;
	
	public Folder(String name) {
		this.name = name;
		notes = new ArrayList<Note>();
	}
	
	public void addNote(Note n) {
		notes.add(n);
	}
	
	public String getName() {
		return name;
	}

	public ArrayList<Note> getNotes() {
		return notes;
	}

	@Override
	public String toString() {
		int nText = 0;
		int nImage = 0;
		
		// TODO
		if (notes.size() != 0)
			for (int i = 0; i < notes.size(); i++) {
				
				if (notes.get(i) instanceof TextNote)
					nText++;
				
				if (notes.get(i) instanceof ImageNote)
					nImage++;
			}
		
		return name + ":" + nText + ":" + nImage;
	}

}
