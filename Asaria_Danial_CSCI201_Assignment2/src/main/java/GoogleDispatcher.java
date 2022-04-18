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
@WebServlet("/GoogleDispatcher")
public class GoogleDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    //private static final String url = "jdbc:mysql://localhost:3306/PA4Users";
    		
    //pay attention to database
    //check pattern
    		
    		//get user id from database
    		//check if email is already registered
    		//send to home if ok
    		
    		//doget is only check if missing data
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("Entered Google Dispatcher");
        response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String error = "";
        String name = request.getParameter("name");

        if (error.equals("")) 
		{
//			System.out.println(error);
//			String cookiename = userID;
//			String value = Integer.toString(id);
//			Cookie cookie = new Cookie(cookiename,value);
//			response.addCookie(cookie);
			response.addCookie(new Cookie("name", URLEncoder.encode(name, StandardCharsets.UTF_8)));
			
			response.sendRedirect("index.jsp");
//			request.setAttribute("name", name);
//	        request.getRequestDispatcher("/home.jsp").forward(request, response);
		}
		else
		{
			request.setAttribute("error", error);
			request.getRequestDispatcher("auth.jsp").include(request, response);
		}
        
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
