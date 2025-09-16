package petstore.testcases;

import static org.hamcrest.Matchers.equalTo;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matcher;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import petstore.endpoints.Storeendpoints;
import petstore.payload.Store;
import petstore.utilities.Helpers;
import static org.hamcrest.Matchers.lessThan;

public class Storetests {
	
	Store storepayload;
	Faker faker;
	public Logger logger;
	
	@BeforeClass
	public void setUp() {
		
		storepayload = new Store();
		faker = new Faker();
		
		storepayload.setId(Helpers.generateNumber());
		storepayload.setPetId(Helpers.generateNumber()+10);
		storepayload.setQuantity(Helpers.generateNumber()+20);
		storepayload.setShipDate(Helpers.generateTimestamp().toString());
		storepayload.setStatus("placed");
		storepayload.setComplete(true);
		
		logger = LogManager.getLogger(this.getClass());
	}
	
	
	@Test(priority=1)
	public void testPostStoreOrder() throws InterruptedException {
		
		logger.info("********** Creating Order **********");
		Response response = Storeendpoints.create_StoreOrder(storepayload);
		response.then().log().all();	
		logger.info("********** Order created **********");
		
		
		logger.info("********** Order creation validation start **********");
		Assert.assertEquals(response.getStatusCode(), 200);
		response.then().assertThat().body("id", equalTo(this.storepayload.getId()));
		response.then().assertThat().body("petId", equalTo(this.storepayload.getPetId()));
		response.then().assertThat().body("quantity", equalTo(this.storepayload.getQuantity()));
		response.then().assertThat().body("status", equalTo(this.storepayload.getStatus()));
		response.then().assertThat().body("complete", equalTo(this.storepayload.getComplete()));
		
		response.then().and().time(lessThan(2000L), TimeUnit.SECONDS);
		
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/Store/StoreSchema.json"));
		logger.info("********** Order created validation completed**********");
		Thread.sleep(2000);
	}


	@Test(priority=2)
	public void testGetStoreOrder() {
		
		logger.info("********** Reading Order info **********");
		Response response = Storeendpoints.get_StoreOrder(this.storepayload.getId());
		response.then().log().all();
		logger.info("********** Read Order info displayed **********");
		
		logger.info("********** Order info validation start **********");
		Assert.assertEquals(response.getStatusCode(), 200);
		response.then().assertThat().body("id", equalTo(this.storepayload.getId()));
		response.then().assertThat().body("petId", equalTo(this.storepayload.getPetId()));
		response.then().assertThat().body("quantity", equalTo(this.storepayload.getQuantity()));
		
		
		logger.info("********** Order info validation completed **********");
	}
	
	@Test(priority=3)
	public void testDeleteStoreOrder() {
		
		logger.info("********** Deleting Order info **********");
		Response response = Storeendpoints.delete_StoreOrder(this.storepayload.getId());
		response.then().log().all();
		logger.info("********** Order info deleted **********");
		
		logger.info("********** Order deletion validation start **********");
		Assert.assertEquals(response.getStatusCode(), 200);
		response.then().assertThat().body("code", equalTo(200));
		response.then().assertThat().body("type", equalTo("unknown"));
		logger.info("********** Order deletion validation completed **********");
	}
	
	
	
	
	
}
