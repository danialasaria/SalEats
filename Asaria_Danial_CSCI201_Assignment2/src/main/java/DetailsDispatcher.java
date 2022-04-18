import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Servlet implementation class GoogleDispatcher
 */
@WebServlet("/DetailsDispatcher")
public class DetailsDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("Entered Google Dispatcher");
        response.setContentType("text/html");
        
        String address = request.getParameter("address");
        address = address.replaceAll("~", " ");
        String phone_number = request.getParameter("phone_number");
        phone_number = phone_number.replaceAll("~", " ");
        String category_name = request.getParameter("category_name");
        category_name = category_name.replaceAll("~", " ");
        String estimated_price = request.getParameter("estimated_price");
        estimated_price = estimated_price.replaceAll("~", " ");
        String rating = request.getParameter("rating");
        Double realRating = Double.parseDouble(rating);
        
        String image_url = request.getParameter("image_url");
        image_url = image_url.replaceAll("~", " ");

        
        String starRating = realRating + " ";
    	int ratinglength = realRating.intValue();
    	//add whole stars
    	for(int i=0;i<ratinglength;i++)
    	{
    		starRating += "&starf;";
    		starRating += " ";
    	}
    	//if decimal then add half star
    	if(ratinglength != realRating)
    	{
    		starRating += "<i class=\"fa fa-star-half\"></i>\n"
    				+ "";
    	}
    	
    	
        String HTMLCODE = ("<div>\n" 
                + "        <div style = \"display: inline-block; padding: 10px;\">\n"
                + "        <img src=" + image_url + " width=\"150\" \n"
                + "        height=\"150\" style=\"border-radius: 10px;\"> </img>\n"
                + "       </div>\n"
                + "\n"
                + "        <div style = \"display: inline-block;\">\n"
                + "       <p>Address: " + address + "</p>\n"
                + "       <p>" + "+" + phone_number + "</p>\n"
                + "       <p>Categories: " + category_name + "</p>\n"
                + "       <p>Price: " + estimated_price + "</p>\n"
                + "       <p>Rating: " + starRating + "</p>\n"
                + "       </div>\n"
                + "       </div>");
        
        request.setAttribute("HTMLCODE", HTMLCODE);
        request.getRequestDispatcher("details.jsp").include(request, response);
        
        //TODO 
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
