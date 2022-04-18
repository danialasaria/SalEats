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
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Util.Constant;


/**
 * Servlet implementation class RegisterDispatcher
 */
@WebServlet("/RegisterDispatcher")
public class RegisterDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String db = "jdbc:mysql://localhost:3306/pa2";
    private static final String user = "root"; 
    private static final String pwd = "root";  //your secret database pwd
    
    
    /**
     * Default constructor.
     */
    public RegisterDispatcher() {
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO
        Pattern namePattern = Pattern.compile("^[ A-Za-z]+$");
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        
        //Temporary email verification
//        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        
    	response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String error = "";
		
		//this may store sensitive information in the url
		String email = request.getParameter("email");
		if (email == null) email = "";
		
		//trim whitespace
		//make name available to every file?
		//two separate home pages for logged in and not ?
		String name = "";
		name = request.getParameter("name");
		if (name == null) name = "";
		String password = request.getParameter("password");
		if (password == null) password = "";
		String confirmpassword = request.getParameter("confirm_password");
		if (confirmpassword == null) confirmpassword = "";
		
		
		if (email.contentEquals("")) {
//			out.println("<h6>Email is missing!</h6>"); 
			error += "<p>Email missing</p>";
		}
		if (name.contentEquals("")) {
//			out.println("<h6>Name is missing!</h6>"); 
			error += "<p> name missing</p>";
		}
		if (password.contentEquals("")) {
//			out.println("<h6>Password is missing!</h6>"); 
			error += "<p>Password is missing</p>";
		}
		if (confirmpassword.contentEquals("")) {
//			out.println("<h6>Confirm Password is missing!</h6>"); 
			error += "<p>choose a confirmpassword please</p>";
		}
		if (!confirmpassword.contentEquals(password)) {
//			out.println("<h6>Confirm Password doesn't match password!</h6>"); 
			error += "<p>Confirm Password doesn't match password!</p>";
		}
		
		//check email pattern
        Matcher matcheremail = emailPattern.matcher(email);
        boolean emailmatch = matcheremail.matches();
        if(emailmatch==false)
        {
//        	out.println("<h6>Email is not formatted right</h6>"); 
			error += "<p>Email is not formatted right</p>";
        }
        Matcher matchername = namePattern.matcher(name);
        boolean namematch = matchername.matches();
        if(namematch==false)
        {
//        	out.println("<h6>Name is not formatted right</h6>"); 
			error += "<p>Name is not formatted right</p>";
        }
        
        //you need an id for sql table
        
        try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (error.equals("")) 
		{
			String sql = "INSERT INTO Students (name, email, password) VALUES (?, ?, ?)";
		    try (Connection conn = DriverManager.getConnection(db, user, pwd);
		    		 PreparedStatement ps = conn.prepareStatement(sql)) {
				     ps.setString(1, name);
				     ps.setString(2, email);
				     ps.setString(3, password);
				     int row = ps.executeUpdate();
				     System.out.println(String.format("Number of rows affected %d",row));
		    } 
		   catch (SQLException ex) 
		    		 {
		    			System.out.println ("SQLException: " + ex.getMessage());
		    	     }
//				     ResultSet rs = ps.executeQuery(); 
//		    		 Statement st = conn.createStatement();
//		    		 ResultSet rs = st.executeQuery(sql);) 
//		    		 while (rs.next()) 
//		    		 {
//			    		 System.out.println (
//			    		 rs.getInt("name") + "\t" +
//			    		 rs.getString("email") + "\t" +
//			    		 rs.getString("password")  );
//		    		 } 

			String cookiename = name;
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
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
