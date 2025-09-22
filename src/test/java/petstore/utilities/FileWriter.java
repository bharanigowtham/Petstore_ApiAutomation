package petstore.utilities;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class FileWriter {
	
	public static void saveResponsetoFile(String fileName, String responseBody) throws Throwable, Exception
	{
		ObjectMapper objMapper = new ObjectMapper();
		JsonNode jsonNode = objMapper.readTree(responseBody);
		
		ArrayNode arrayNode;
		
		String Path = "./Responses/"+generateTimestamp();
		
		File folderPath = new File(Path);
		if(!folderPath.exists()) {
			folderPath.mkdirs();
		} 
		
		 String filePath = Path+"/"+fileName+".json";
		 File file = new File(filePath);
		 if(!file.exists()) {
			 file.createNewFile();
		 } else {
			 
		 }
		 
		 if(file.exists() && file.length() > 0) {
			 arrayNode = (ArrayNode) objMapper.readTree(file);
		 }
		 else {
			 arrayNode = objMapper.createArrayNode();
		 }
		 
		 arrayNode.add(jsonNode);
		 
		 objMapper.writerWithDefaultPrettyPrinter().writeValue(file, arrayNode);
		
	}
	
	public static String generateTimestamp() {
			
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
		return ldt.format(format);
	}
		
}
