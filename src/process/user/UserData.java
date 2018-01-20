package process.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UserData {
	public static String getUserID(String firstname, String lastname,String email,String birthyear) {
		String result=null;

		String uri =
				"https://sde-restaurant-rec-pws.herokuapp.com/process/user/id?firstname="+firstname+"&lastname="+lastname+"&email="+email+"&birthyear="+birthyear;
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
