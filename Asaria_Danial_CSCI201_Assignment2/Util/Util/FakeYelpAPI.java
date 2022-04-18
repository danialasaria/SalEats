package Util;


import com.google.gson.*;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale.Category;

/**
 * A class that pretends to be the Yelp API
 */
public class FakeYelpAPI {
    private static Boolean ready = false;
    private static final String db = "jdbc:mysql://localhost:3306/pa2";
    private static final String user = "root"; 
    private static final String pwd = "root";  //your secret database pwd

    /**
     * Initializes the DB with json data
     *
     * @param responseString the Yelp json string
     */
    public static void Init(String responseString) {
        if (ready) {
            return;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //TODO check if you've done the initialization
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ready = true;
        //TODO get businessHelper array from json
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        
        try {
        Gson gson = 
        	    new GsonBuilder()
        	        .registerTypeAdapter(new TypeToken<ArrayList<Restaurant>> () {}.getType(), new BusinessDeserializer())
        	        .registerTypeAdapter(new TypeToken<ArrayList<Categories>> () {}.getType(), new CategoriesDeserializer())
        	        .create();
        BusinessWrapper BusinessObject = gson.fromJson(responseString, BusinessWrapper.class);
        restaurants = (ArrayList<Restaurant>) BusinessObject.getBusinesses();
        System.out.println("Data read!");
        } catch(Exception e) {
        	e.printStackTrace();
            System.out.println("Data parsing didn't work");
        }
        
        Integer detailsID = 0;
        Integer ratingID = 0;
        Integer categoryID = 0;
        //TODO iterate the businessHelper array and insert every business into the DB
//        Integer count = 1;
        try (Connection conn = DriverManager.getConnection(db, user, pwd)) {
        		for(Restaurant r: restaurants) { 
        			System.out.println(restaurants.size());
            		String insertDetails = "INSERT INTO Restaurant_details (image_url, address, phone_no, estimated_price, yelp_url) VALUES (?, ?, ?, ?, ?)";
            		PreparedStatement id = conn.prepareStatement(insertDetails);
//            		id.setInt(1, count);
//            		count++;
            		 id.setString(1, r.image_url);
   				     id.setString(2, r.location.getLocation());
   				      id.setString(3, r.phone);
				     id.setString(4, r.price);
				     id.setString(5, r.url);
					 int idRow = id.executeUpdate();
					 String selectDetailsID = "SELECT MAX(details_id) AS details_id from Restaurant_details";
					 PreparedStatement dID = conn.prepareStatement(selectDetailsID);
//					 dID.setString(1,r.image_url);
				     ResultSet rs = dID.executeQuery(); 
				     rs.next();
		    	 	 detailsID = rs.getInt("details_id");

				     String insertRating = "INSERT INTO Rating_details (review_count, rating) VALUES (?, ?)";
	        		PreparedStatement ir = conn.prepareStatement(insertRating);
	   				ir.setInt(1, r.review_count);
	   				ir.setDouble(2, r.rating);
				    int iraRow = ir.executeUpdate();
				    String selectRatingID = "SELECT MAX(rating_id) AS rating_id from Rating_details";
					 PreparedStatement rID = conn.prepareStatement(selectRatingID);
				     ResultSet rsa = rID.executeQuery(); 
				     rsa.next();
		    	 	 ratingID = rsa.getInt("rating_id");

	   				String insertRestaurant = "INSERT INTO Restaurant (restaurant_id, restaurant_name, details_id, rating_id) VALUES (?, ?, ? , ?)";
        			PreparedStatement ps = conn.prepareStatement(insertRestaurant);
   				     ps.setString(1, r.id);
   				     ps.setString(2, r.name);
   				     ps.setInt(3, detailsID);
   				     ps.setInt(4, ratingID);
 				    int ireRow = ps.executeUpdate();

	   				//what category for what restaurant id
	   				//least dependency to most dependency
            		String insertCategory = "INSERT INTO Category (category_name, restaurant_id) VALUES (?,?)";
            		PreparedStatement ic = conn.prepareStatement(insertCategory);
            		ic.setString(1, r.getCategories());
            		ic.setString(2, r.id);
				    int ircRow = ic.executeUpdate();
				    String selectCategoryID = "SELECT MAX(category_id) AS category_id from Category";
					PreparedStatement cID = conn.prepareStatement(selectCategoryID);
				    ResultSet rsb = cID.executeQuery(); 
				    rsb.next();
		    	 	categoryID = rsb.getInt("category_id");

		    	 	String insertRestaurantCategories = "INSERT INTO RestaurantCategories (category_id, restaurant_id) VALUES (?,?)";
            		PreparedStatement irc = conn.prepareStatement(insertRestaurantCategories);
            		irc.setInt(1, categoryID);
            		irc.setString(2, r.id);
				    irc.executeUpdate();

																										
//				    System.out.println(String.format("Number of rows affected %d",ireRow));
//				    System.out.println(String.format("Number of rows affected %d",idRow));
//				    System.out.println(String.format("Number of rows affected %d",iraRow));
//				    System.out.println(String.format("Number of rows affected %d",ircRow));
        		}
        }		
        catch (SQLException ex) 
	    		 {
	    			System.out.println ("SQLException: " + ex.getMessage());
	    	     }
    }

    public static Business getBusiness(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //TODO return business based on id
        return null;
    }

    /**
     * @param keyWord    the search keyword
     * @param sort       the sort option (price, review count, rating)
     * @param searchType search in category or name
     * @return the list of business matching the criteria
     */
    public static ArrayList<Business> getBusinesses(String keyWord, String sort, String searchType) {
        ArrayList<Business> busisnesses = new ArrayList<Business>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //TODO get list of business based on the param
        return busisnesses;

    }
}

//Code adapted from https://stackoverflow.com/questions/23070298/get-nested-json-object-with-gson-using-retrofit
class BusinessDeserializer implements JsonDeserializer<Restaurant> {
    @Override
    public Restaurant deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonElement content = je.getAsJsonObject();
        return new Gson().fromJson(content, Restaurant.class);
    }
}

class CategoriesDeserializer implements JsonDeserializer<Restaurant> {
    @Override
    public Restaurant deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonElement content = je.getAsJsonObject();
        return new Gson().fromJson(content, Restaurant.class);
    }
}


//PreparedStatement ire = conn.prepareStatement(insertRestaurant);
//PreparedStatement id = conn.prepareStatement(insertDetails);
//PreparedStatement ira = conn.prepareStatement(insertRating);
//PreparedStatement ic = conn.prepareStatement(insertCategory);
//        			Statement s = conn.createStatement();
//String sql = "INSERT INTO Students (name, email, password) VALUES (?, ?, ?)";
//try (Connection conn = DriverManager.getConnection(db, user, pwd);
//		 PreparedStatement ps = conn.prepareStatement(sql)) {
//	     ps.setString(1, name);
//	     ps.setString(2, email);
//	     ps.setString(3, password);
//	     int row = ps.executeUpdate();