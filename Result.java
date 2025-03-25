

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ResultServlet extends HttpServlet {
	Connection con =null;
	PreparedStatement pstm =null;
	ResultSet rs = null;
	String driver ="com.mysql.cj.jdbc.Driver";
;
	String url = "jdbc:mysql://localhost:3306/august?user=root&password=NITHIN@7";
	String sql = "Select * from result where id=? and password=?";
	
	
	
	public void init() {
		try {
			
			//Connection Establishment code
			Class.forName(driver);
			System.out.println("Driver executed");
			con = DriverManager.getConnection(url);
			System.out.println("Connection established");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void service(HttpServletRequest req, HttpServletResponse res)  {
		try {
			//client request and executing the query
			pstm = con.prepareStatement(sql);
			String rollno=req.getParameter("rollno");
			String password = req.getParameter("password");
			if (rollno == null || rollno.trim().isEmpty()||password == null||password.trim().isEmpty()) {
	            res.sendRedirect("/UniversityResultStudent/error.html"); // Redirect if roll number is empty
	            return;
	        }
			int id=Integer.parseInt(rollno);
		
			pstm.setInt(1, id);
			pstm.setString(2,password);
			rs=pstm.executeQuery();
			//response code
			res.setContentType("text/html");
			PrintWriter pw =res.getWriter();
			//Fetching the data
			if(rs.next()) {
				String name = rs.getString(2);
				int m1=rs.getInt(4);
				int m2= rs.getInt(5);
				int m3= rs.getInt(6);
				
				//Storing the data in session for dispatching code(added to session)
				HttpSession session=req.getSession(true);
				session.setAttribute("marks1", m1);
				session.setAttribute("marks2", m2);
				session.setAttribute("marks3", m3);

				
				
				//float percentage = ((float)(m1+m2+m3)/300)*100;
				//Printing the data of result
				

				pw.println("</body></html>");
				pw.println("<!DOCTYPE html>");
				pw.println("<html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>");
				pw.println("<title>Your Result</title>");
				pw.println("<style>");
				pw.println("body { font-family: 'Poppins', sans-serif; background: linear-gradient(135deg, #2196F3, #4CAF50); margin: 0; padding: 20px; display: flex; justify-content: center; align-items: center; height: 100vh; }");
				pw.println(".container { width: 50%; background: rgba(255, 255, 255, 0.1); padding: 20px; border-radius: 12px; box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.2); backdrop-filter: blur(10px); border: 1px solid rgba(255, 255, 255, 0.3); text-align: center; }");
				pw.println("h2 { color: #fff; margin-bottom: 20px; font-size: 24px; }");
				pw.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; background: rgba(255, 255, 255, 0.2); border-radius: 10px; overflow: hidden; }");
				pw.println("th, td { padding: 12px; text-align: center; font-size: 16px; color: white; }");
				pw.println("th { background: rgba(0, 0, 0, 0.3); }");
				pw.println("tr:nth-child(even) { background: rgba(255, 255, 255, 0.1); }");
				pw.println("tr:hover { background: rgba(0, 0, 0, 0.3); transition: 0.3s; }");
				pw.println("</style></head><body>");

				pw.println("<div class='container'>");
				pw.println("<h2>" + name + " - Your Result</h2>");
				pw.println("<table>");
				pw.println("<thead>");
				pw.println("<tr><th>Subject</th><th>Marks</th></tr>");
				pw.println("</thead>");
				pw.println("<tbody>");
				pw.println("<tr><td>Mathematics (M1)</td><td>" + m1 + "</td></tr>");
				pw.println("<tr><td>English (Eng)</td><td>" + m2 + "</td></tr>");
				pw.println("<tr><td>Science (PC)</td><td>" + m3 + "</td></tr>");
				pw.println("</tbody>");
				pw.println("</table>");
				pw.println("</div>");

				pw.println("</body></html>");

				
				
				//Dispatching request to calculatePercentage
				req.getServletContext().getRequestDispatcher("/calculatePercentage").include(req, res);

			}
			else {
	            res.sendRedirect("error.html"); // Redirect if no data found
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        try {
	            res.sendRedirect("error.html"); // Redirect in case of exception
	        } catch (IOException ioException) {
	            ioException.printStackTrace();
	        }
	    }
	}

}
