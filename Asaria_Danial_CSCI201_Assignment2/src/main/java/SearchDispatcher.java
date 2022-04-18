import javax.servlet.ServletConfig;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import Util.Categories;
import Util.Constant;
import Util.FakeYelpAPI;
import Util.Restaurant;
import Util.RestaurantBackend;

import static com.google.gson.JsonParser.parseReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serial;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Servlet implementation class SearchDispatcher
 */
@WebServlet("/SearchDispatcher")
public class SearchDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String db = "jdbc:mysql://localhost:3306/pa2";
    private static final String user = "root"; 
    private static final String pwd = "root";  //your secret database pwd
    /**
     * Default constructor.
     */
    public SearchDispatcher() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        // TODO get json file as stream, Initialize FakeYelpAPI by calling its initalize
        // method
        
        //read it as a string
        InputStream stream = servletContext.getResourceAsStream(Constant.FileName);
         try {
			FakeYelpAPI.Init(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         
//        Gson gson = new Gson();
//        String FileName = "src/main/webapp/restaurant_data.json";
//        BufferedReader reader;
//        Restaurant[] restaurants = new Restaurant[0];
//		try {
//			reader = new BufferedReader(new FileReader(FileName));
//	        JsonArray businessHelper = parseReader(reader).getAsJsonArray();
//            restaurants = gson.fromJson(businessHelper, Restaurant[].class);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    for(Restaurant restaurant: restaurants)
//        {
//            for(Categories categories: restaurant.getCategories())
//            {
//                System.out.println(categories.title);
//            }
//        }
//	    System.out.println(restaurants);
        
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO
        response.setContentType("text/html");
    	PrintWriter out = response.getWriter();
    	//This is the word they searched 
        String searched = request.getParameter("searched");
        
        //how they want to sort the results
        String sortOption = request.getParameter("sortOption");
        
        //search by name or category
        String category = request.getParameter("category");
        
        //phrase that will show to user
        String searchPhrase = "Results for " + searched + " in " + category;
        
        //need to append this html based on the search query
       
        String header = "<h2 style=\"color:grey;\">" + searchPhrase
        + "<hr>";
        
        //
        String imageUrl = "PLEASE ASSING";
        Integer reviewCount = 100000000;
        String starRating = "ASSIGN THIS";
        
        //if search by name go to restaurant database and use like word to sift restaurant_name
        //for the set that matches use details id to go to restaurant_details and find estimated price and
        //yelp url and image url
        //use ratingid to go to rating details and find the review count and rating
        
        //if search by category use like word to sift and get category id in category table,
        //then go to restaurantcategories get restaurant ids in an array list
        //then go to restaurants and match the ids and then do steps above        
        
        
        
        //for loop to do this for every result?
        ArrayList<RestaurantBackend> restaurants = new ArrayList<RestaurantBackend>();
        try (Connection conn = DriverManager.getConnection(db, user, pwd)) {
//    		for(Restaurant r: restaurants) { 
        		if(category.equals("category"))
        		{
        			String categoryQuery = "SELECT restaurant_id, category_id, category_name FROM Category WHERE category_name LIKE" + " '%" + searched + "%'";
            		PreparedStatement cQ = conn.prepareStatement(categoryQuery);
    			    ResultSet cQue = cQ.executeQuery();
    			    while(cQue.next())
    			    {
    			    	RestaurantBackend temp = new RestaurantBackend();
    			    	temp.restaurant_id = cQue.getString("restaurant_id");
    			    	temp.category_id = cQue.getInt("category_id");
    			    	temp.category_name = cQue.getString("category_name");
    			    	restaurants.add(temp);
    			    }
    			    
    			    //get to same step as name procedure
    			    for(RestaurantBackend r: restaurants)
    			    {
	    			    String queryRD = "SELECT restaurant_name, details_id, rating_id FROM Restaurant WHERE restaurant_id='" + r.restaurant_id + "'";
		        		PreparedStatement RD = conn.prepareStatement(queryRD);
					    ResultSet rs = RD.executeQuery();
					    while(rs.next())
					    {
					    	r.details_id = rs.getInt("details_id");
					    	r.rating_id = rs.getInt("rating_id");
					    	r.restaurant_name = rs.getString("restaurant_name");
					    }
    			    }     			    
        		}
        		
        		//only want to create restaurants once
        		if(category.equals("name"))
        		{
	    	        String queryRD = "SELECT restaurant_id, restaurant_name, details_id, rating_id FROM Restaurant WHERE restaurant_name LIKE" + " '%" + searched + "%'";
	        		PreparedStatement RD = conn.prepareStatement(queryRD);
				    ResultSet rs = RD.executeQuery();
				    while(rs.next())
				    {
				    	RestaurantBackend temp = new RestaurantBackend();
				    	temp.details_id = rs.getInt("details_id");
				    	temp.rating_id = rs.getInt("rating_id");
				    	temp.restaurant_id = rs.getString("restaurant_id");
				    	temp.restaurant_name = rs.getString("restaurant_name");
				    	restaurants.add(temp);
				    }
        		
			    
			    //get categoryid to get category
				    for(RestaurantBackend r: restaurants)
				    {
					    String cID = "SELECT category_id FROM RestaurantCategories WHERE restaurant_id='" + r.restaurant_id + "'";
					    PreparedStatement cI = conn.prepareStatement(cID);
					    ResultSet c = cI.executeQuery();
					    while(c.next())
					    {
					    	r.category_id = c.getInt("category_id");
					    }
				    }
        		
			    
				    //use restaurantid to get category name
				    for(RestaurantBackend r: restaurants)
				    {
					    String cNA = "SELECT category_name FROM Category WHERE category_id=" + r.category_id;
					    PreparedStatement cN = conn.prepareStatement(cNA);
					    ResultSet c = cN.executeQuery();
					    while(c.next())
					    {
					    	r.category_name = c.getString("category_name");
					    }
				    }
        		}
			    
			    for(RestaurantBackend r: restaurants)
			    {
			    	Integer detailsID = r.details_id;
				    String epyu = "SELECT image_url, phone_no, address, estimated_price, yelp_url FROM Restaurant_details WHERE details_id=" + detailsID;
				    PreparedStatement ep = conn.prepareStatement(epyu);
				    ResultSet epy = ep.executeQuery();
				    while(epy.next())
				    {
				    	r.image_url = epy.getString("image_url");
				    	r.address = epy.getString("address");
				    	r.phone_number = epy.getString("phone_no");
				    	r.estimated_price = epy.getString("estimated_price");
				    	r.yelp_url = epy.getString("yelp_url");
				    }
			    }
			    
			    for(RestaurantBackend r: restaurants)
			    {
			    	Integer ratingID = r.rating_id;
				    String rcr = "SELECT review_count, rating FROM Rating_details WHERE rating_id=" + ratingID;
				    PreparedStatement rc = conn.prepareStatement(rcr);
				    ResultSet ra = rc.executeQuery();
				    while(ra.next())
				    {
				    	r.review_count = ra.getInt("review_count");
				    	r.rating = ra.getDouble("rating");
				    }
			    }
			    
			    }	
        catch (SQLException ex) 
        		 {
        			System.out.println ("SQLException: " + ex.getMessage());
        	     }
//        ArrayList<String> HTMLCODE = new ArrayList<String>();
        
        
        ArrayList<RestaurantBackend> sortedRestaurants = new ArrayList<RestaurantBackend>();
        //handle sorting selection
        if(sortOption.equals("reviewCount"))
        {
            ArrayList<Integer> list = new ArrayList<>();
        	for(RestaurantBackend r: restaurants)
    	    {
        		list.add(r.review_count);
    	    }
        	Collections.sort(list);
        	Collections.reverse(list);
            for(Integer i: list)
    	    {
            	for(RestaurantBackend r: restaurants)
        	    {
            		if(r.review_count == i)
            		{
            			sortedRestaurants.add(r);
                		restaurants.remove(r);
                		break;
            		}
        	    }
    	    }
        }
        
        if(sortOption.equals("price"))
        {
            ArrayList<String> list = new ArrayList<>();
        	for(RestaurantBackend r: restaurants)
    	    {
        		if(r.estimated_price!=null)
        		{
            		list.add(r.estimated_price);
        		}
    	    }
        	Collections.sort(list);
        	Collections.reverse(list);
            for(int i=0;i<list.size();i++)
    	    {
            	for(RestaurantBackend r: restaurants)
        	    {
            		if(r.estimated_price !=null && r.estimated_price.equals(list.get(i)))
            		{
            			sortedRestaurants.add(r);
                		restaurants.remove(r);
                		break;
            		}
        	    }
    	    }
            
            //add all the restaurants with no estimated price to the end of the data set
            for(RestaurantBackend r: restaurants)
    	    {
        		if(r.estimated_price == null)
        		{
        			r.estimated_price = "not available";
        			sortedRestaurants.add(r);
        		}
    	    }
        }
        
        if(sortOption.equals("rating"))
        {
            ArrayList<Double> list = new ArrayList<>();
        	for(RestaurantBackend r: restaurants)
    	    {
        		list.add(r.rating);
    	    }
        	Collections.sort(list);
        	Collections.reverse(list);
            for(Double i: list)
    	    {
            	for(RestaurantBackend r: restaurants)
        	    {
            		if(r.rating == i)
            		{
            			sortedRestaurants.add(r);
                		restaurants.remove(r);
                		break;
            		}
        	    }
    	    }
        }
        
        
        
        
        
        
        
        
        String HTMLCODE = header;
        for(RestaurantBackend r: sortedRestaurants)
	    {
        	if(r.estimated_price == null)
    		{
    			r.estimated_price = "not available";
    		}
        	String rating = "";
        	int ratinglength = r.rating.intValue();
        	//add whole stars
        	for(int i=0;i<ratinglength;i++)
        	{
        		rating += "&starf;";
        		rating += " ";
        	}
        	//if decimal then add half star
        	if(ratinglength != r.rating)
        	{
        		rating += "<i class=\"fa fa-star-half\"></i>\n"
        				+ "";
        	}
        	
        	//replace the space with the ~ character so it can be a url
        	String address = r.address;
        	if(address != null)
        		{
        			address = address.replaceAll("\\s", "~");
        		}
        	String phone = r.phone_number;
        	if(phone != null)
    		{
            	phone = phone.replaceAll("\\s", "~");
    		}
        	String cat = r.category_name;
        	if(cat != null)
    		{
            	cat = cat.replaceAll("\\s", "~");
    		}
        	String eprice = r.estimated_price;
        	if(eprice != null)
    		{
            	eprice = eprice.replaceAll("\\s", "~");
    		}
//        	String rRating = r.rating;
//        	if(rRating != null)
//    		{
//            	rRating = rRating.replaceAll("\\s", "~");
//    		}  
        	String iURL = r.image_url;    
        	if(iURL != null)
    		{
            	iURL = iURL.replaceAll("\\s", "~");
    		}          
        	
        	String detailsLink = "DetailsDispatcher?address=" + address + "&phone_number=" + phone + "&category_name=" + cat + "&estimated_price=" + eprice + "&rating=" + r.rating + "&image_url=" + iURL; 
        	//convert rating to stars
            HTMLCODE += ("<div>\n" 
                    + "        <div style = \"display: inline-block; padding: 10px;\">\n"
                    + "        <img src=" + r.image_url + " width=\"150\" \n"
                    + "        height=\"150\" style=\"border-radius: 10px;\"> </img>\n"
                    + "       </div>\n"
                    + "\n"
                    + "        <div style = \"display: inline-block;\">\n"
                    + "       <a href=" + detailsLink +">" + r.restaurant_name + "</a>\n"
                    + "       <p>Price: " + r.estimated_price + "</p>\n"
                    + "       <p>Review Count: " + r.review_count + "</p>\n"
                    + "       <p>Rating: " + r.rating + " " + rating + "</p>\n"
                    + "       <a href="+r.yelp_url+">Yelp Link</a>\n"
                    + "       </div>\n"
                    + "       </div>\n"
                    + "       <hr>");
            
	    }
        request.setAttribute("HTMLCODE", HTMLCODE);
//        		id.setInt(1, count);
//        		count++;
//        		 id.setString(1, r.image_url);
//				     id.setString(2, r.location.getLocation());
//				      id.setString(3, r.phone);
//			     id.setString(4, r.price);
//			     id.setString(5, r.url);
//				 int idRow = id.executeUpdate();
//				 String selectDetailsID = "SELECT MAX(details_id) AS details_id from Restaurant_details";
//				 PreparedStatement dID = conn.prepareStatement(selectDetailsID);
////				 dID.setString(1,r.image_url);
//			     ResultSet rs = dID.executeQuery(); 
//			     rs.next();
//	    	 	 detailsID = rs.getInt("details_id");

//		response.addCookie(new Cookie("searched", URLEncoder.encode(searched, StandardCharsets.UTF_8)));
//		response.addCookie(new Cookie("searchPhrase", URLEncoder.encode(searchPhrase, StandardCharsets.UTF_8)));
//		response.addCookie(new Cookie("HTMLCODE", URLEncoder.encode(HTMLCODE, StandardCharsets.UTF_8)));
		request.getRequestDispatcher("search.jsp").include(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}