package customs.utils;

import java.util.ArrayList;
import java.util.Iterator;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class  Formatting {

	
	public static ArrayList<String> extractMiniPaths(ArrayList<String> longPath) {
		System.out.println(longPath);
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
	
	public static ArrayList<String> extractMiniPathsAndFile(ArrayList<String> longPath) {
		System.out.println(longPath);
		ArrayList<String> splittedPaths = new ArrayList<>();
		Iterator<String> it = longPath.iterator();
		String filePath;
		String[] folders;
		while(it.hasNext()) {
			filePath = it.next();
			 folders = filePath.split("/");
			 for (int i=0; i < folders.length;i++) {
				 if ((i<=0) && (!splittedPaths.contains(folders[i])))
				   splittedPaths.add(folders[i]);
				 else 
					 if ((i>0) && (!splittedPaths.contains(folders[i-1]+"/"+folders[i]))) {
						 int j=0;
						 while(j<i) {
							 splittedPaths.add(folders[i-1]+"/"+folders[i]);
							 j++;
						 }
						
					 }
					  
				} 
			 }
	   return splittedPaths;
	} 
	
	
	public static String encodeToBase64(String str){
		// encode data on your side using BASE64
		//https://stackoverflow.com/questions/19743851/base64-java-encode-and-decode-a-string
		String   bytesEncoded = Base64.encode(str.getBytes());
		return bytesEncoded;
	}
	
	public static String decodeFromBase64(String bytesEncoded){

		byte[] valueDecoded= Base64.decode(bytesEncoded);
		System.out.println("Decoded value is " + new String(valueDecoded));
		
		return new String(valueDecoded);
	}
	
}
