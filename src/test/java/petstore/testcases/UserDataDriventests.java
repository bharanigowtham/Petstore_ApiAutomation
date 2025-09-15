package petstore.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import petstore.endpoints.Userendpoints;
import petstore.payload.User;
import petstore.utilities.DataProviders;

public class UserDataDriventests {
		
	
	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class)
	public void testPostUser(String id, String username, String firstname, String lastname, String email, String password, String phone) {
		 
		User userpayload = new User();
		
		userpayload.setId(Integer.parseInt(id));
		userpayload.setUsername(username);
		userpayload.setFirstName(firstname);
		userpayload.setLastName(lastname);
		userpayload.setEmail(email);
		userpayload.setPassword(password);
		userpayload.setPhone(phone);
		
		Response response = Userendpoints.createUser(userpayload);	
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	
	@Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String Name) throws InterruptedException {
			
		 Response response = Userendpoints.deleteUser(Name);
		 response.then().log().all();
		 Assert.assertEquals(response.getStatusCode(), 200);
		 
		}
	}
