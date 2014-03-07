/*This class is run in the background for every one hour using a Cron job.
 * It retrieves the product details of the customer's saved and hits the Zappos search API to see 
 * if the price of the product is dropped by 20% off the original price, if it is yes, the code then pushes a email
 * notification to the customer.
 * 
 * */
package Notifier;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.github.sendgrid.SendGrid;
public class PriceDropNotifier {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, ParseException{
	
	      	Connection conn =openConnection();
	      	int customerId =0;
	        int productId=0 ;
	        String ProductName=null ;
	        float productPrice=0;
	      String query = "SELECT * FROM productdata";
	      Statement st = conn.createStatement();
	      ResultSet rs = st.executeQuery(query);
	      while (rs.next())
	      {
	         customerId = rs.getInt("CustomerId");
	         productId = rs.getInt("ProductID");
	         ProductName = rs.getString("ProductName");
	         productPrice=rs.getFloat("ProductPrice");
	        System.out.println("customerId "+customerId+"productId "+productId+"ProductName "+ProductName+"productPrice "+productPrice);
	       
	      }
	      if(getCurrentPrice(ProductName,productPrice)){
	    	  String email=getCustomerEmail(customerId);
	    	 pushNotification(ProductName,productId,email);  
	      }
	}
	
	public static boolean getCurrentPrice(String productName,float savedPrice) throws IOException, ParseException{
		float currentPrice=0;
		boolean isDiscounted=false;
		URL url = new URL("http://api.zappos.com/Search?term="+productName+"&key=5b8384087156eb88dce1a1d321c945564f4d858e");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		if (connection.getResponseCode() != 200) {
			throw new IOException(connection.getResponseMessage());
		}
		// Buffer the result into a string
		BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder responseString = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) 
			responseString.append(line);
		rd.close();
		System.out.println(responseString);
		connection.disconnect();
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(responseString.toString());
		 
		JSONObject jsonObject = (JSONObject) obj;
 
		JSONArray msg = (JSONArray) jsonObject.get("results");
		Iterator<JSONObject> iterator = msg.iterator();
		while (iterator.hasNext()) {
			JSONObject obj1 = iterator.next();
			String price=obj1.get("price").toString();
			currentPrice=Float.parseFloat(price);
		}
		
		if(currentPrice<(savedPrice-(.2*savedPrice)))
			isDiscounted=true;
		return isDiscounted;
	}
	
	public static String getCustomerEmail(int customerId) throws ClassNotFoundException, SQLException{
		Connection con=openConnection();
		String emailQuery="select E-Mail from customerinfo";
		Statement st = con.createStatement();
	      ResultSet rs = st.executeQuery(emailQuery);
	      String email=null;
	      while (rs.next())
	      {
	        email=rs.getString("E-Mail");
	      }
		
		return email;
	}
	
	public static Connection openConnection() throws ClassNotFoundException, SQLException{
		
		  String myDriver = "com.mysql.jdbc.Driver";
	      String myUrl = "jdbc:mysql://localhost:3306/zapalerts";
	      Class.forName(myDriver);
	      Connection conn = DriverManager.getConnection(myUrl, "root", "root");
	      
	      return conn;
	}
	
	//Uses sendgrid API to push the notifications
	public static void pushNotification(String ProductName,int productId,String email){
			String username = "venkateshthallam";
		    String password = "passwordforsendgrid";//dummy password
		    
		    SendGrid sendgrid = new SendGrid(username, password);

		    sendgrid.addTo(email);
		    sendgrid.setFrom("venkatesh.thallam@nyu.edu");
		    sendgrid.setSubject("Hurry Up! The Product "+ProductName+" is on sale now");
		    sendgrid.setText("Hey, The product you saved is on sale now with more than 20% discount. Go, Zap it, we mean, grab it! :) ");

		    sendgrid.send();
	}
}
