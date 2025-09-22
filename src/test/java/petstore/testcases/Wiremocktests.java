package petstore.testcases;

import static io.restassured.RestAssured.given;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static com.github.tomakehurst.wiremock.client.WireMock.*;


public class Wiremocktests {
	
	private static WireMockServer wireMockServer;
	
	@BeforeMethod
	public void MockServer_setup() {
			System.out.println("Starting Mock server");
			
			wireMockServer = new WireMockServer(8089);
			wireMockServer.start();	
			RestAssured.baseURI = "http://localhost:"+ wireMockServer.port();
			
			wireMockServer.stubFor(post(urlPathEqualTo("/store/order"))
	                .willReturn(aResponse()
	                        .withStatus(200)
	                        .withHeader("Content-Type", "application/json")
	                        .withBody("{ \"id\": 1, "
	                        		+ "\"petId\": 101,"
	                        		+ "\"quantity\": 2," 
	                        		+ "\"shipDate\": \"2025-09-16T08:00:22.376+0000\"," 
	                        		+ "\"status\": \"placed\", "
	                        		+ "\"complete\": true }")));
	}
	
	@AfterMethod
	public void MockServer_Teardown() {
		wireMockServer.stop();
		System.out.println("Mock server stopped");
	}
	
	@Test
	public void testPostuseruisngMockserver() {
	
		Response response = given().body("{\"petId\": 202,  \"quantity\": 1,  \"shipDate\": \"2024-06-20T14:58:00.123Z\",  \"status\": \"placed\",  \"complete\": true}")
								.header("Content-Type","application/json")
								.accept("application/json")
								.when().post("/store/order");
		response.then().log().all();
		System.out.println("Status code is: " + response.getStatusCode());
		
	}
}
