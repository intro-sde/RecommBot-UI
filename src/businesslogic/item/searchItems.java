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


	public static List<String> listItems(String city, String type) {
		String result="Items in list items";
		String City = city;
		String Type=type;
		System.out.println(City);
		System.out.println(Type);
		String uri =
				"https://sde-item-search-ws.herokuapp.com/search?keyword="+City+"%20"+Type;
		
		//https://sde-item-search-ws.herokuapp.com/search?keyword=Mysore%20restaurant
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
				System.out.println(list);
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
