<%@page import="com.jspiders.springmvc.dto.blogDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.jspiders.springmvc.dto.User"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 20px;
}

.container {
	background-color: white;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	padding: 20px;
	max-width: 500px;
	margin: auto;
}

table {
	width: 100%;
	margin: 20px 0;
}

td {
	padding: 10px;
	text-align: left;
}

input[type="text"], textarea {
	width: 100%;
	padding: 8px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

textarea {
	resize: vertical; /* Allow vertical resizing */
}

input[type="submit"] {
	background-color: #007bff;
	color: white;
	padding: 10px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	width: 100%;
	margin-top: 10px;
}

input[type="submit"]:hover {
	background-color: #0056b3;
}

h3 {
	color: red;
	text-align: center;
}

a {
	display: block;
	text-align: center;
	margin-top: 15px;
	color: #007bff;
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}
</style>
</head>
<body>
  <%
	//User user = (User) request.getAttribute("user");
    blogDTO blogDTO=(blogDTO)request.getAttribute("blog");
	%>
	<div align="center">
		<form action="./update-blog" method="post">
			<table border="1px solid">
				<tr>
					<td>id</td>
					<td><input type="text" name="id" 
						value="<%=blogDTO.getId()%>" readonly="readonly"></td>
				</tr>
				<tr>
					<td>Title</td>
					<td><input type="text" name="title" required="required"
						value="<%=blogDTO.getTitle()%>"></td>
				</tr>
				<tr>
					<td>Content</td>
					<td><input type="text" name="content" required="required"
						value="<%=blogDTO.getContent()%>"></td>
				</tr>
				<tr>
					<td>Date</td>
					<td><input type="text" name="date" required="required"
						value="<%=blogDTO.getDate()%>"></td>
				</tr>
				<tr>
					<td>Auther</td>
					<td><input type="text" name="auther" required="required"
						value="<%=blogDTO.getAuthor()%>"></td>
				</tr>
			</table>
			<input type="submit" value="Update">
		</form>
	</div> 
</body>
</html>