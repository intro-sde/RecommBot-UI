package process.item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class DailyQuotes {

	public static String getRandomQuotes(String type) {
		String result="Quotes";
		String uri=null;
		
		
		if (type.equalsIgnoreCase("activity")) {
			uri = "https://sde-activity-rec-pws.herokuapp.com/process/quotes";
		} else {
			uri ="https://sde-restaurant-rec-pws.herokuapp.com/process/quotes";
		}
		
		URL url;
		
		try {
			url = new URL(uri);

			HttpURLConnection connection =
					(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			BufferedReader in = new BufferedReader
					(new InputStreamReader(connection.getInputStream()));
			String inputLine=null;

			while ((inputLine = in.readLine()) != null) {
				
				result=(inputLine);
		
			}
		
			in.close();


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
	

