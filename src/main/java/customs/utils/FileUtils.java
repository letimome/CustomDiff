package customs.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;


public class FileUtils {
	

	
	private static PrintWriter out;
	
	
	
	public static  void writeToFile(String path, String text){
		
		try {
			//System.out.println("File path:"+path);
			//System.out.println("File text:"+text);
			File file = new File(path);
			
			file.getParentFile().mkdirs(); // Will create parent directories if not exists
			file.createNewFile();
			FileOutputStream s = new FileOutputStream(file,false);
			s.write(text.getBytes());
			s.close();
			System.out.println("Wrote a file in path:"+path);
			
		} catch (Exception e) {
			System.out.println("Could not find file with:"+path);
			e.printStackTrace();
		}
		
	}
	
	

	public static void writeToFile(String path, ArrayList<String> allInserts) {
		Iterator<String> it = allInserts.iterator();
		String st;
			
			try {
				 out = new PrintWriter(path);
				while (it.hasNext()){
				  st=it.next();
				  out.append(st);
				}
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}



	public static String readFromFile(String path) {
		
	BufferedReader br;
	try {
		br = new BufferedReader(new FileReader(path));
		StringBuilder sb = new StringBuilder();
	    String line = br.readLine();
	
	    while (line != null) {
	        sb.append(line);
	        sb.append(System.lineSeparator());
	        line = br.readLine();
	    }
	    String everything = sb.toString();
	    br.close();
	    return everything;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	
	}
}
