package petstore.endpoints;

public class Routes {
	
	public static String base_Url = "https://petstore.swagger.io/v2";
	
	//User Module
	public static String post_url = base_Url + "/user";
	public static String get_url = base_Url + "/user/{username}";
	public static String put_url = base_Url + "/user/{username}";
	public static String delete_url = base_Url + "/user/{username}";

}
