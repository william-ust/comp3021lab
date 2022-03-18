package base;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Note implements Comparable<Note>, Serializable{
	private Date date;
	private String title;
	
	public Note(String title) {
		this.title = title;
		date = new Date(System.currentTimeMillis());
	}
	
	public String getTitle() {
		return title;
	}
	
	public Date getDate() {
		return date;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		return Objects.equals(title, other.title);
	}
	
	@Override
	public int compareTo(Note o) {
		// equal
		if (this.getDate().compareTo(o.getDate()) == 0)
			return 0;
		
		// this.date is before o.date
		if (this.getDate().compareTo(o.getDate()) < 0)
			// greater
			return 1;
		
		// smaller
		return -1;
	}
	
	public String toString() {
		return date.toString() + "\t" + title;
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
