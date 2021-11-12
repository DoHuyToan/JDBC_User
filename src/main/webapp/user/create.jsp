<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/12/2021
  Time: 9:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>USER MANAGEMENT</h1>
<h2> <a href="/users?action=users">LIST ALL USERS</a> </h2>
<center>
    <div align="center">
        <form method="post">
            <table border="1" cellpadding="5">
                <caption> <h2>ADD NEW USER</h2> </caption>
                <tr>
                    <th>User Name:</th>
                    <td>
                        <input type="text" name="name" id="name" size="45"/>
                    </td>
                </tr>
                <tr>
                    <th>User Email:</th>
                    <td>
                        <input type="text" name="email" id="email" size="45"/>
                    </td>
                </tr>
                <tr>
                    <th>Country:</th>
                    <td>
                        <input type="text" name="country" id="country" size="15"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="Save"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</center>
</body>
</html>
