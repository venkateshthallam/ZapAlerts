/*
 * This servlet makes a call to the Zappos search API by taking input from the search page and 
 * displays all the relevant products. It converts the json response from the API to java objects and redirects to
 * results jsp page
 * 
 * 
 * */
package zapAlerts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class SearchServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String searchTerm=request.getParameter("search");
		URL url = new URL("http://api.zappos.com/Search?term="+searchTerm+"&key=12c3302e49b9b40ab8a222d7cf79a69ad11ffd78");
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
		List<ZapposBean> resultList = new ArrayList<ZapposBean>();
		 
		try {
	 
			Object obj = parser.parse(responseString.toString());
	 
			JSONObject jsonObject = (JSONObject) obj;
	 
			// loop array
			JSONArray msg = (JSONArray) jsonObject.get("results");
			Iterator<JSONObject> iterator = msg.iterator();
			while (iterator.hasNext()) {
				JSONObject obj1 = iterator.next();
				ZapposBean z1 = new ZapposBean();
				z1.setStyleId(obj1.get("styleId").toString());
				z1.setProductId(obj1.get("productId").toString());
				z1.setProductName(obj1.get("productName").toString());
				z1.setOriginalPrice(obj1.get("originalPrice").toString());
				z1.setPrice(obj1.get("price").toString());
				z1.setThumbnailImageUrl(obj1.get("thumbnailImageUrl").toString());
				
				resultList.add(z1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("ResultList", resultList);
		RequestDispatcher rd1=request.getRequestDispatcher("result.jsp");
		rd1.forward(request,response);
	}

}
