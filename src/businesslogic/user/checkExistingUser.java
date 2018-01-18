package businesslogic.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.json.JSONException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.recombee.api_client.exceptions.ApiException;



public class checkExistingUser  {

	public static String checkUser(String firstname, String lastname,String email,String birthyear) {
		// check in recombee if user exists

		//	"https://sde-storage-ws.herokuapp.com/rdb/users"

		//if yes, disply message "already exists"



		System.out.println(firstname);
		System.out.println(lastname);
		System.out.println(email);
		System.out.println(birthyear);

		int j=0;
		List<String> list=new LinkedList<>();
		list=checkExistingUser.listUsers();
		String result=null;

		for (int i=0;i<list.size();i++) {
			String message_text=list.get(i).toString();

			if(message_text.contains(firstname)&message_text.contains(lastname)&message_text.contains(email)&message_text.contains(birthyear)){
				j=j+1;
			}
			result = "User is already registered";
		}



		if (j==0) {


			String uri =
					"https://sde-storage-ws.herokuapp.com/rdb/users?firstname="+firstname+"&lastname="+lastname+"&email="+email+"&birthyear="+birthyear;
			URL url;
			result = "New user is registered";
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

		}


		return result;
	}


	public static List<String> listUsers() {
		String result="User in list users";
		String uri =
				"https://sde-storage-ws.herokuapp.com/rdb/users";
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
				//System.out.println(list);
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
