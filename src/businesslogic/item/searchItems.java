package businesslogic.item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class searchItems {


	public static List<String> listItems(String userId,String city, String type) {
		String result="Items in list items";

		String uri ="https://sde-recommendation-ws.herokuapp.com/recommend?userId="+userId+"&type="+type+"&city="+city;
	
		URL url;
		List<String> list = new LinkedList<>();
		try {
			url = new URL(uri);

			HttpURLConnection connection =
					(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");


			BufferedReader in = new BufferedReader
					(new InputStreamReader(connection.getInputStream()));
			String inputLine=null;

			while ((inputLine = in.readLine()) != null) {

				list.add(inputLine);
				
				
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
		return list;
	}
	
	
}
