package base;

import java.util.ArrayList;

public class NoteBook {
	
	private ArrayList<Folder> folders;
	
	public NoteBook() {
		folders = new ArrayList<Folder>();
	}
	
	public boolean createTextNote(String folderName, String title) {
		TextNote note = new TextNote(title);
		return insertNote(folderName, note);
	}
	
	public boolean createImageNote(String folderName, String title) {
		ImageNote note = new ImageNote(title);
		return insertNote(folderName, note);
	}
	
	public ArrayList<Folder> getFolders() {
		return folders;
	}
	
	public boolean insertNote(String folderName, Note note) {
		Folder f = null;
		
		// step 1
		for (Folder f1 : folders) {
			if (f1.getName().equals(folderName))
				f = f1;
		}
		
		if (f == null) {
			f = new Folder(folderName);
			folders.add(f);
		}
			
		// step 2
		for (Note n : f.getNotes()) {
			if (n.getTitle().equals(note.getTitle())) {
				System.out.println("Creating note " + note.getTitle() + " under folder " + folderName + " failed");
				return false;
			}
		}
		
		f.addNote(note);
		return true;
	}
	
}
