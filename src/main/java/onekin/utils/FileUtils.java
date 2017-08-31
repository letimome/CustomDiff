package onekin.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;


public class FileUtils {
	

	
	private static PrintWriter out;
	
	
	
	public static  void writeToFile(String path, String text){
		
		try {
			out = new PrintWriter(path);
			out.print(text);
			out.close();
			
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
