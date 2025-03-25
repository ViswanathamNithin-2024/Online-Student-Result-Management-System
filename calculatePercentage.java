

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class calculatePercentage extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res)  {
		try {
			PrintWriter pw = res.getWriter();
			HttpSession hs =  req.getSession();
			int m1 = (int) hs.getAttribute("marks1");
			int m2 = (int)hs.getAttribute("marks2");
			int m3 = (int)hs.getAttribute("marks3");
			
			float percentage  = ((float)(m1+m2+m3)/300)*100;
			pw.println("<table><tr><td>Percentage :</td><td>" + percentage + "</td></tr></table>");

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
 