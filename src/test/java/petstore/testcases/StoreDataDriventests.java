package petstore.testcases;

import static org.hamcrest.Matchers.equalTo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import petstore.endpoints.Storeendpoints;
import petstore.payload.Store;
import petstore.utilities.DataProviders;
import petstore.utilities.Helpers;


public class StoreDataDriventests {
	
	public Logger logger = LogManager.getLogger(this.getClass());;
	
	@Test(priority=1, dataProvider="StoreData", dataProviderClass=DataProviders.class)
	public void testPostStoreOrder_DD(String id, String petId, String quantity, String shipDate, String status, String complete)  { 
		
		logger.info("********** Creating Store Order from DD **********");
		
		Store storepayload = new Store();
		
		storepayload.setId(Integer.parseInt(id));
		storepayload.setPetId(Integer.parseInt(petId));
		storepayload.setQuantity(Integer.parseInt(quantity));
		storepayload.setShipDate(shipDate);
		if(storepayload.getShipDate().equalsIgnoreCase("Random")) {
			storepayload.setShipDate(Helpers.generateTimestamp().toString());
		}
		storepayload.setStatus(status);
		
		boolean complete_bool = Boolean.parseBoolean(complete);
		storepayload.setComplete(complete_bool);
				
		Response response = Storeendpoints.create_StoreOrder(storepayload);
							response.then().log().all();
		
		logger.info("********** Store Order created from DD **********");
	}
	
	@Test(priority=2, dataProvider="id", dataProviderClass=DataProviders.class)
	public void testDeleteStoreOrderByID_DD(String orderId)  {
		
			logger.info("********** Deleting Store Order by ID from DD **********");
			
		 Response response = Storeendpoints.delete_StoreOrder(Integer.parseInt(orderId));
		 response.then().log().all();
		 
		 	logger.info("********** Store Order deleted by ID from DD **********");
		 
		}
	
	@Test(priority=3, dataProvider="id", dataProviderClass=DataProviders.class)
	public void testGetStoreOrderByID_DD_Negative(String orderId)  {
		
		logger.info("********** Getting Store Order by ID from DD - Negative **********");
		
		Response response = Storeendpoints.get_StoreOrder(Integer.parseInt(orderId));
		 response.then().log().all();
		 
		 response.then().body("message", equalTo("Order not found"));
		 
		 logger.info("********** Store Order fetched by ID from DD - Negative **********");
	}
	
}
