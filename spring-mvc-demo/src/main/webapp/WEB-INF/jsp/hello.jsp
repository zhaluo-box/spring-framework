<%--
  Created by IntelliJ IDEA.
  User: wmz
  Date: 2022/7/31
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello page</title>
</head>
<body>
<h1> spring mvc learn</h1>
<br/>
<form id="helloInfo" method="post" action="/hello/">
    姓名: <input type="text" name="name"> <br/>
    昵称: <input type="text" name="nickName"><br/>
    地址: <input type="text" name="address"><br/>
    <input type="submit" value="提交"><br/>
</form>

</body>
</html>
