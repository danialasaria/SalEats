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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class LoginDispatcher
 */
@WebServlet("/LoginDispatcher")
public class LoginDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String db = "jdbc:mysql://localhost:3306/pa2";
    private static final String user = "root"; 
    private static final String pwd = "root";  //your secret database pwd

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO
    	
    	//check to see if data matches the database
    	
    	//get name from database
    	response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String error = "";
		
		String email = request.getParameter("email");
		if (email == null) email = "";
		System.out.println(email);

		String password = request.getParameter("password");
		if (password == null) password = "";
		
		String name = "";
		String databaseName = "";
		String databaseEmail = "";
		String databasePassword = "";
		//for testing
		 try {
	    		Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		String sql = "SELECT * FROM Students WHERE email=?";
	    try (Connection conn = DriverManager.getConnection(db, user, pwd);
	    		 PreparedStatement ps = conn.prepareStatement(sql)) {
			     ps.setString(1, email);
			     ResultSet rs = ps.executeQuery(); 
			     while (rs.next()) 
		    		 {
			    	 	 databaseEmail = rs.getString("email");
			    	 	 databasePassword = rs.getString("password");
			    	 	 databaseName = rs.getString("name");
			    		 System.out.println (
			    		 rs.getString("name") + "\t" +
			    		 rs.getString("email") + "\t" +
			    		 rs.getString("password")  );
		    		 } 
			     if(databaseEmail==null)
			     {
			    	 error = "Please login with the correct email or sign up if you haven't already";
			     }
			     else if(!(databasePassword.equals(password)))
			     {
			    	 System.out.println (databasePassword);
			    	 System.out.println (password);
			 		 error = "Please login with the correct password";
			     }
			     else {
			    	  name = databaseName;
			     }
			     
	    }
			     catch (SQLException ex) 
	    		 {
	    			System.out.println ("SQLException: " + ex.getMessage());
	    	     }
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
