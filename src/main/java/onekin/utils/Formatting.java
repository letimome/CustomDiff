package onekin.utils;

import java.util.ArrayList;
import java.util.Iterator;

public class  Formatting {

	
	public static ArrayList<String> extractMiniPaths(ArrayList<String> longPath) {
		ArrayList<String> splittedPaths = new ArrayList<>();
		Iterator<String> it = longPath.iterator();
		String filePath;
		String[] folders;
		while(it.hasNext()) {
			filePath = it.next();
			 folders = filePath.split("/");
			 for (int i=0; i < folders.length-1;i++) {
				 if ((i<=0) && (!splittedPaths.contains(folders[i])))
				   splittedPaths.add(folders[i]);
				 else 
					 if ((i>0) && (!splittedPaths.contains(folders[i-1]+"/"+folders[i])))
					   splittedPaths.add(folders[i-1]+"/"+folders[i]);
				} 
			 }
	   return splittedPaths;
	} 
	
	
}
