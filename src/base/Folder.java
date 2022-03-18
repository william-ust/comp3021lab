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
	
	// Not Functional
	public List<Note> searchNotes(String keywords) {
		List<Note> result = new ArrayList<Note>();
		Stack<String> andList = new Stack<String>();
		Stack<String> orList = new Stack<String>();
		
		String[] separate_key = keywords.split(" ");
		
		for (int i = 0; i < separate_key.length; i++) {
			separate_key[i] = separate_key[i].toLowerCase();
			
			if (separate_key[i] != "or") {
				andList.push(separate_key[i]);
			}
			
			else if (separate_key[i] == "or") {

				String temp = andList.pop();
				orList.push(temp);
				orList.push(separate_key[i+1]);
				i+=1;
			}
		}
		
		List<Note> temp_result = new ArrayList<Note>();
		
		for (String s : orList) {
			for (Note n : notes) {
				if (n.getTitle().toLowerCase().contains(s)) {
					temp_result.add(n);
					continue;
				}
				
				if (n instanceof TextNote)
					if (((TextNote)n).content.toLowerCase().contains(s))
						temp_result.add(n);

			}
		}
		
		for (Note n : temp_result) {
			int num_match = 0;
			for (String s : andList) {
				if (n.getTitle().toLowerCase().contains(s)) {
					num_match++;
					continue;
				}
				
				if (n instanceof TextNote)
					if (((TextNote)n).content.toLowerCase().contains(s))
						num_match++;
			}
			
			if (num_match == andList.size())
				result.add(n);
		}
		if (andList.size() == 0)
			return temp_result;
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

}
