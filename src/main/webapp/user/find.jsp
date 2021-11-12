<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/12/2021
  Time: 10:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>LIST USER BY COUNTRY</h1>
<p> <a href="/users">Back to User List</a> </p>
<form method="post">
    <table>
        <tr>
            <td>ENTER COUNTRY NAME</td>
            <td><input type="text" name="country" placeholder="Nhập tên đất nước"></td>
            <td colspan="2" align="center"><input type="submit" value="Find"></td>
        </tr>

        <c:if test="${users != null}">
            <table border="1">
                <caption><h2>LIST USER BY COUNTRY</h2></caption>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Country</th>
                </tr>
                <c:forEach var="user" items='${requestScope["users"]}'>
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.country}"/></td>
                    </tr>
                </c:forEach>
            </table>

        </c:if>
    </table>
</form>



</body>
</html>
