package zapAlerts;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveProducts extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con=null;
		try{
			con=DBcon.connect();
			PreparedStatement ps  = con.prepareStatement("insert into productdata(ProductID, ProductName, ProductPrice) values(?,?,?) ");
			String[] variable=request.getParameterValues("selected");
			for(String str : variable){
				StringTokenizer tokens = new StringTokenizer(str, ",");
				int productId = Integer.parseInt(tokens.nextToken());
				String productName = tokens.nextToken();
				float price = Float.parseFloat(tokens.nextToken());
				ps.setInt(1, productId);
				ps.setString(2, productName);
				ps.setFloat(3, price);
				ps.executeUpdate();
			}
			RequestDispatcher rd=request.getRequestDispatcher("saveProducts.jsp");
			rd.forward(request,response);
		}catch (ClassNotFoundException e) {
			request.setAttribute("message", "Server connection failed..please try again");
			RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
			rd.forward(request,response);
		}catch (SQLException e) {
			request.setAttribute("message", "User cannot be created");
			RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
			rd.forward(request,response);
		}finally{
			try {
				if(con != null){
					con.close();
				}
			} 
			catch (SQLException e) {
				request.setAttribute("message", "Server busy..please try again later");
			}
			catch(NullPointerException e) {
				request.setAttribute("message", "Server not connected..please try again later");
			}
	    }
		
	}

}
