//package Util;
//
//
//import com.google.gson.*;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import static com.google.gson.JsonParser.parseReader;
//
//
///**
// * A class that pretends to be the Yelp API
// */
//@WebServlet("/RestaurantDataParser")
//public class RestaurantDataParser extends HttpServlet {
//    private static Boolean ready = false;
//    private static final long serialVersionUID = 1L;
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
//        System.out.println("I am here");
//	    
//	    //WHERE DO I SEND POST REQUEST?- in search dispatcher?
//	   // request.setAttribute("data", restaurants);
//	    
//        
//        //TODO iterate the businessHelper array and insert every business into the DB
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//      System.out.println("HIIIII");
//      Gson gson = new Gson();
//      String FileName = "src/main/webapp/restaurant_data.json";
//      BufferedReader reader;
//      Restaurant[] restaurants = new Restaurant[0];
//		try {
//			reader = new BufferedReader(new FileReader(FileName));
//	        JsonArray businessHelper = parseReader(reader).getAsJsonArray();
//            restaurants = gson.fromJson(businessHelper, Restaurant[].class);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////	    ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();
//	    for(Restaurant restaurant: restaurants)
//      {
//          for(Categories categories: restaurant.getCategories())
//          {
//              System.out.println(categories.title);
//          }
//      }
//	    System.out.println(restaurants);
//    }
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        doGet(request, response);
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