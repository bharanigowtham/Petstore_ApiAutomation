package petstore.utilities;

import java.time.Instant;
import java.util.Random;

public class Helpers {
	
	public static int generateNumber() {
	    Random random = new Random();
	    return random.nextInt(9) + 1; 
	}
	
	public static Instant generateTimestamp() {
	    return Instant.now();
	}
}
