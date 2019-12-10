<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>信贷中介管理系统主页</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <script src="/static/JQuery/jquery-2.2.3.min.js"></script>

</head>
<body >
<button id="btn">ss</button>
${ sessionScope.user}
<p id="p1"></p>
</body>
<script type="text/javascript">
    $('#btn').click(function () {
        var username = '<%= session.getAttribute("user")%>';
        console.log("username:::" + username);

    })

</script>
</html>