//package Util;
//
//
//import com.google.gson.*;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//
//import javax.servlet.annotation.WebServlet;
//
//import static com.google.gson.JsonParser.parseReader;
//
//
///**
// * A class that pretends to be the Yelp API
// */
//@WebServlet("/RestaurantParser")
//public class RestaurantParser {
//    private static Boolean ready = false;
//
//    /**
//     * Initializes the DB with json data
//     *
//     * @param responseString the Yelp json string
//     */
//    public static void Init(String responseString) { 
//        if (ready) {
//            return;
//        }
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            //TODO check if you've done the initialization
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        ready = true;
//        //TODO get businessHelper array from json
//        Gson gson = new Gson();
//        String FileName = "src/main/webapp/restaurant_data.json";
//        BufferedReader reader;
//        RestaurantParser[] restaurants = new RestaurantParser[0];
//		try {
//			reader = new BufferedReader(new FileReader(FileName));
//	        JsonArray businessHelper = parseReader(reader).getAsJsonArray();
//            restaurants = gson.fromJson(businessHelper, RestaurantParser[].class);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    ArrayList<RestaurantParser> restaurantList = new ArrayList<RestaurantParser>();
//	    for(RestaurantParser restaurant: restaurantList)
//        {
//            for(Categories categories: restaurant.categories)
//            {
//                System.out.println(categories.title);
//            }
//        }
//	    System.out.println(restaurantList);
//	    
//	    //WHERE DO I SEND POST REQUEST?- in search dispatcher?
//	   // request.setAttribute("data", restaurants);
//	    
//        
//        //TODO iterate the businessHelper array and insert every business into the DB
//    }
//
//    public static Business getBusiness(String id) {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        //TODO return business based on id
//        return null;
//    }
//
//    /**
//     * @param keyWord    the search keyword
//     * @param sort       the sort option (price, review count, rating)
//     * @param searchType search in category or name
//     * @return the list of business matching the criteria
//     */
//    public static ArrayList<Business> getBusinesses(String keyWord, String sort, String searchType) {
//        ArrayList<Business> busisnesses = new ArrayList<Business>();
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        //TODO get list of business based on the param
//        return busisnesses;
//
//    }
//}
//
////Code adapted from https://stackoverflow.com/questions/23070298/get-nested-json-object-with-gson-using-retrofit
////class BusinessDeserializer implements JsonDeserializer<BusinessHelper> {
////    @Override
////    public BusinessHelper deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
////        JsonElement content = je.getAsJsonObject();
////        return new Gson().fromJson(content, BusinessHelper.class);
////    }
////}