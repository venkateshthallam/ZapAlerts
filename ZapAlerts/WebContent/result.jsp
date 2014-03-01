<!-- This jsp page takes the result set of products from the search servlet and displays it. 
It also provides an option to select the items and save it to the database -->
<%@page import="java.util.ArrayList"%>
<%@page import="zapAlerts.ZapposBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table>
	<form action="saveProducts" method="GET">
		<%
			ArrayList<ZapposBean> results = (ArrayList<ZapposBean>) request.getAttribute("ResultList");
			if (results != null) {
				for (ZapposBean res : results) {
		%>
		<tr>
			<%String details = res.getProductId() + "," + res.getProductName() + ","  + res.getOriginalPrice();%>
			<td><input type="checkbox" name="selected" value=<%=details %>></td>
			<td><img src="<%=res.getThumbnailImageUrl()%>" /><td>
			<td valign="middle"><b><%=res.getStyleId()%></b></td>
			<td valign="middle"><b><%=res.getProductId()%></b></td>
			<td valign="middle"><b><%=res.getProductName()%></b></td>
			<td valign="middle"><b><%=res.getOriginalPrice()%></b></td>
			<td valign="middle"><b><%=res.getPrice()%></b></td>
		</tr>
		<%
			}
			}
		%>
		<input type="submit" name="submit"/>
		</form>
	</table>
</body>
</html>