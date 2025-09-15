package petstore.testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import petstore.endpoints.Userendpoints;
import petstore.payload.User;

public class Usertests {
	
	Faker faker;
	User userpayload;
	public Logger logger;
	
	@BeforeClass
	public void setUp() {
		
		faker = new Faker();
		userpayload = new User();
		
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5,10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
		logger = LogManager.getLogger(this.getClass());	
	}
	
	@Test(priority=1)
	public void testCreateUser() throws InterruptedException {
		
		logger.info("********** Creating User **********");
		
		Response response = Userendpoints.createUser(userpayload);
		response.then().log().all();
		
		System.out.println("Username is: " + this.userpayload.getUsername());

		Assert.assertEquals(response.getStatusCode(), 200);
		Thread.sleep(2000);
		
		logger.info("********** User Created **********");
	}
	
	@Test(priority=2)
	public void testGetUserByName() throws InterruptedException {
		
		logger.info("********** Reading User Info **********");
			
		 Response response = Userendpoints.getUser(this.userpayload.getUsername());
		 response.then().log().all();
		 
		 System.out.println("Username is: " + this.userpayload.getUsername());
		 System.out.println("Firstname is: " + this.userpayload.getFirstName());
		 System.out.println("LastName is: " + this.userpayload.getLastName());
		 System.out.println("Email is: " + this.userpayload.getEmail());

		 Assert.assertEquals(response.getStatusCode(), 200);
		 
		 logger.info("********** User Info Displayed **********");
	}
	
	@Test(priority=3)
	public void testUpdateUserbyName() {
		
		logger.info("********** Updating User Info **********");
		
		 //update data using payload
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		
		 Response response = Userendpoints.updateUser(this.userpayload.getUsername(), userpayload);
		 response.then().log().body();
	 
		 //checking data after update
		 Response responseafterupdate = Userendpoints.getUser(this.userpayload.getUsername());
		 responseafterupdate.then().log().body();
		 Assert.assertEquals(responseafterupdate.getStatusCode(), 200);
		 
		 System.out.println("Username is: " + this.userpayload.getUsername());
		 System.out.println("Firstname is: " + this.userpayload.getFirstName());
		 System.out.println("LastName is: " + this.userpayload.getLastName());
		 System.out.println("Email is: " + this.userpayload.getEmail());

		 Assert.assertEquals(response.getStatusCode(), 200);
		 
		 logger.info("********** User Info Updated **********");
	}
	
	@Test(priority=4)
	public void testDeleteUserByName() {
		
		 logger.info("********** Deleting User **********");
		
		 Response response = Userendpoints.deleteUser(this.userpayload.getUsername());
		 response.then().log().all();
		 System.out.println("Username is: " + this.userpayload.getUsername());
		 System.out.println(response.getStatusCode());
		 Assert.assertEquals(response.getStatusCode(), 200);
		 
		 logger.info("********** User Deleted **********");
	}
	
//	@Test(priority=5)
	public void testGetUserByName_Negative() {
		
		logger.info("********** Reading User Info (Negative Test) **********");
		
		 Response response = Userendpoints.getUser(this.userpayload.getUsername());
		 response.then().log().all();
		 System.out.println(response.getStatusCode());
		 Assert.assertEquals(response.getStatusCode(), 200);
		 
		 logger.info("********** User Info Not Displayed (Negative Test) **********");
	}
	
	
	
	
	
	
	
}
