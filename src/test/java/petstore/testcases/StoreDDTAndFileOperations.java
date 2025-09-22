package petstore.testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import petstore.endpoints.Storeendpoints;
import petstore.payload.Store;
import petstore.utilities.DataProviders;
import petstore.utilities.FileWriter;
import petstore.utilities.Helpers;

public class StoreDDTAndFileOperations {
	
public Logger logger = LogManager.getLogger(this.getClass());;
	
	@Test(priority=1, dataProvider="StoreData", dataProviderClass=DataProviders.class)
	public void testPostStoreOrder_DD(String id, String petId, String quantity, String shipDate, String status, String complete) throws Throwable  { 
		
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
		
		logger.info("********** Saving Post Store Order response to file **********");
		
		 try {
			 String fileName = "CreateStoreOrderResponse";
			 String respbody = response.getBody().asString();
			 FileWriter.saveResponsetoFile(fileName, respbody); 
		 }
		 
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		 
		  logger.info("********** Post Store Order response saved to file **********");
		
	}
	

	@Test(priority=2, dataProvider="id", dataProviderClass=DataProviders.class)
	public void testGetStoreOrderByID_DD(String orderId) throws Throwable  {
		
		logger.info("********** Getting Store Order by ID from DD**********");
		
		Response response = Storeendpoints.get_StoreOrder(Integer.parseInt(orderId));
		 response.then().log().all();
		 
		 logger.info("********** Store Order fetched by ID from DD**********");
		 
		 logger.info("********** Saving Get Store Order response to file **********");
		 
		 try {
			 String fileName = "GetStoreOrderResponse";
			 String respbody = response.getBody().asString();
			 FileWriter.saveResponsetoFile(fileName, respbody); 
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		  
		 logger.info("********** Get Store Order response saved to file **********");
	}
	
	
}
