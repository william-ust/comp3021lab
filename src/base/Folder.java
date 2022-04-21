package base;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Folder implements Comparable<Folder>, Serializable {
	
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
	
	@Override
	public int compareTo(Folder o) {
		if (this.getName().compareTo(o.getName()) < 0)
			return -1;
		
		if (this.getName().compareTo(o.getName()) == 0)
			return 0;
		
		return 1;
			
	}
	
	public void sortNotes() {
		Collections.sort(notes);
	}
	
	// Fixed searchNotes
	public List<Note> searchNotes(String keywords) {
		ArrayList<Note> result = new ArrayList<Note>();
		
		// ignore capital letter and split the keywords using space
		String[] keyList = keywords.toLowerCase().split(" ");
		
		for (int i = 0; i < keyList.length; i++) {
			ArrayList<Note> temp = new ArrayList<Note>();
			
			// if the next word is an or operator
			if (i + 1 < keyList.length && keyList[i + 1].equals("or")) {
				
				// search every Note in notes for (keyword keyList[i] and keyword keyList[i + 2])
				for (Note n : notes) {
					
					// if it is ImageNote
					if (n instanceof ImageNote) {
						
						// if it contains the keyword
						if ( 	n.getTitle().toLowerCase().contains(keyList[i]) || 
								n.getTitle().toLowerCase().contains(keyList[i + 2]))
							temp.add(n);
					}
					
					// if it is TextNote
					else if (n instanceof TextNote) {
						
						// if it contains the keyword in title
						if (	n.getTitle().toLowerCase().contains(keyList[i]) ||
								n.getTitle().toLowerCase().contains(keyList[i + 2]))
							temp.add(n);
						
						// if it contains the keywords in content
						else if ( 	( (TextNote) n ).content.toLowerCase().contains(keyList[i]) || 
									( (TextNote) n ).content.toLowerCase().contains(keyList[i + 2]))
							temp.add(n);
							
					}
				}
				
				// directly store the result if first time
				if (i == 0)
					result.addAll(temp);
				else
					// AND
					result.retainAll(temp);
				
				// update progress 
				// finished processing keyList[i + 2]
				i += 2;
			
			// if next word is a keyword
			} else {
				
				// search every Note in notes for (keyword keyList[i])
				for (Note n : notes) {
					
					// if it is ImageNote
					if (n instanceof ImageNote) {
						
						// if it contains the keyword
						if (n.getTitle().toLowerCase().contains(keyList[i]))
							temp.add(n);
					}
					
					// if it is TextNote
					else if (n instanceof TextNote) {
						
						// if it contains the keyword in title
						if (n.getTitle().toLowerCase().contains(keyList[i]))
							temp.add(n);
						
						// if it contains the keywords in content
						else if ( ( (TextNote) n ).content.toLowerCase().contains(keyList[i]))
							temp.add(n);	
					}
				}
				
				// directly store the result if first time		
				if (i == 0)
					result.addAll(temp);
				else
					// AND
					result.retainAll(temp);	
			}
		}
		
		return result;
	}
	
	
	public boolean save(String file) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			
			out.writeObject(this);
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean removeNotes(String title) {
		Note note = null;
		
		for (Note n : notes) {
			if (n.getTitle().equals(title)) {
				note = n;
				break;
			}
		}
	
		if (note != null)
			return notes.remove(note);
		else
			return false;
	}
}
