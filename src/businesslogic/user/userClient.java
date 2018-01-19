package businesslogic.user;

import java.util.List;

import soap.User;
import soap.UserImplService;

public class userClient {

	public static String checkExistingUser(String firstname, String lastname, String email, String birthyear) {
	
		UserImplService us=new UserImplService();
		User u = us.getUserImplPort();
		//List<String> user=u.listUsers();		
		
		String output=u.checkUser(firstname, lastname, email, birthyear);
		
		return output;
	}
}
