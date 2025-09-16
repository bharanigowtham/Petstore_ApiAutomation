package petstore.endpoints;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import petstore.payload.Store;

public class Storeendpoints {
	
	public static Response create_StoreOrder(Store payload) {
		
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
								.when().post(Routes.post_Order_url);
		
		return response;
	}
	
	public static Response get_StoreOrder(int orderid) {
		
		Response response = given().pathParam("orderId", orderid)
							.when().get(Routes.get_Order_Url);
		
		return response;
	}
	
	public static Response delete_StoreOrder(int orderid) {
		
		Response response = given().pathParam("orderId", orderid)
						.when().delete(Routes.delete_Order_Url);
		
		return response;
	}
	
}
