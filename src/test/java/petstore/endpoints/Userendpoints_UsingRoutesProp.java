package petstore.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import petstore.payload.User;

public class Userendpoints_UsingRoutesProp {
	
	public static ResourceBundle getUrl() {
		
		ResourceBundle routes = ResourceBundle.getBundle("routes");
		return routes;
	}
	
	
	public static Response createUser(User payload) {
		
		String post_url = getUrl().getString("post_url");
		
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
							.when().post(post_url);
		return response;
		
	}
	
	public static Response getUser(String userName) {
		
		Response response = given().pathParam("username", userName)
						    .when().get(getUrl().getString("get_url"));
		return response;
		
	}
	
	
	public static Response updateUser (String userName, User payload) {
		
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("username", userName).body(payload)
							.when().put(getUrl().getString("update_url"));
		return response;
		
	}
	
	public static Response deleteUser(String userName) {
		
		Response response = given().pathParam("username", userName)
						    .when().delete(getUrl().getString("delete_url"));
		return response;
		
	}
}
