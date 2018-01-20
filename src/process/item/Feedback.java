package process.item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Feedback {


	public static String saveUserRating(String userId,String itemId, String rating) {
		String result="Preference and rating are saved!";

		String uri ="https://sde-restaurant-rec-pws.herokuapp.com/process/feedback?itemId="+itemId+"&userId="+userId+"&rating="+rating;

		URL url;
		
		try {
			url = new URL(uri);

			HttpURLConnection connection =
					(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			
			BufferedReader in = new BufferedReader
					(new InputStreamReader(connection.getInputStream()));
			String inputLine=null;

			while ((inputLine = in.readLine()) != null) {
				result=(inputLine).toString();
				
			}


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