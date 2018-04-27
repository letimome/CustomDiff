package customs.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;


public class FileUtils {
	

	
	private static PrintWriter out;
	
	
	
	public static  void writeToFile(String path, String text){
		
		try {
			
			File file = new File(path);
			file.getParentFile().mkdirs(); // Will create parent directories if not exists
			file.createNewFile();
			FileOutputStream s = new FileOutputStream(file,false);
			s.write(text.getBytes());
		
			s.close();

			
		} catch (Exception e) {
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

	

}
