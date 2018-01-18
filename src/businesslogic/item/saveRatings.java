package businesslogic.item;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class saveRatings {

		
	public static String saveUserRating(String userId,String itemId, String rating) {
	String result="Ratings are saved!";
		
	String uri ="https://sde-storage-ws.herokuapp.com/rdb/ratings?userId="+userId+"&itemId="+itemId+"&rating="+rating;
		
	URL url;
	List<String> list = new LinkedList<>();
	try {
		url = new URL(uri);
		
		HttpURLConnection connection =
				(HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Accept", "application/json");
		
		
		InputStream xml = connection.getInputStream();

		
		connection.disconnect();
	
	}
	catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
	} 
	return result;
}

	
	
}
