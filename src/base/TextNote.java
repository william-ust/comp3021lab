package base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class TextNote extends Note{
	String content;
	
	public TextNote(String title) {
		super(title);
	}
	
	public TextNote(String title, String content) {
		super(title);
		this.content = content;
	}
	
	public TextNote(File f) {
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
	}
	
	private String getTextFromFile(String absolutePath) {
		String result = "";
		try (FileInputStream fis = new FileInputStream(absolutePath);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader (isr);) {
			
			String temp = br.readLine();
			while (temp != null) {
				result += temp;
				temp = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void exportTextToFile(String pathFolder) {
		if (pathFolder == "")
			pathFolder = ".";
		
		String temp_title = this.getTitle();
		if (temp_title.contains(" "))
			temp_title = temp_title.replaceAll(" ", "_");
		
		try {
			File file = new File(pathFolder + File.separator + temp_title + ".txt");
			file.createNewFile();
			
			FileWriter fw = new FileWriter(file);
			
			fw.write(content);
			fw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
}
