package petstore.testcases;

import static io.restassured.RestAssured.given;
import java.io.FileInputStream;
import java.util.Properties;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class AuthenticationTests {
	Properties prop,prop2;
	FileInputStream file,file2;
	
	@BeforeClass
	public void setUp() throws Throwable {
		
		prop = new Properties();
		prop2 = new Properties();
		file = new FileInputStream("src\\test\\resources\\config.properties");
		file2 = new FileInputStream("src\\test\\resources\\routes.properties");
		prop.load(file);
		prop2.load(file2);
	}
	
	@Test(priority=1)
	public void testAuthentication() {
		
				 Response response = given().auth().basic(prop.getProperty("BasicAuthUsername"), prop.getProperty("BasicAuthPassword"))
				.when().get(prop2.getProperty("Auth_check_url"));
				 
				 response.then().log().all();
				 response.then().body("authenticated", equalTo(true));
				 
	}
	
	@Test(priority=2)
	public void testApiKey() {
		
		Response response = given().relaxedHTTPSValidation().queryParam("q", "Tamilnadu").queryParam("cnt", "1").queryParam("appid", prop.getProperty("apiKey"))
							.when().get(prop2.getProperty("APIKey_check_url"));
		
		response.then().log().all();
	}
	
}
