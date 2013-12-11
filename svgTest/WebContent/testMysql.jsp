<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.sql.*"%>
<html>

<body>

<%
	String url = "jdbc:mysql://127.0.0.1:3306/jdbc";
	String user = "root";
	String password = "root";
	String svgfilename = "";
	try {
		//加载驱动程序
		Class.forName("com.mysql.jdbc.Driver");

		//连接数据库
		Connection conn = (Connection) DriverManager.getConnection(url,
				user, password);

		//statement用来执行SQL语句
		Statement statement = conn.createStatement();

		//要执行的SQL语句
		String sql = "select name from svgnames";

		//结果集
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			svgfilename = svgfilename + rs.getString(1) + ";";

		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	out.println(svgfilename);
%>

</body>

</html>

