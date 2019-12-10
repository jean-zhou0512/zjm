<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${orders.id}
</body>
<script type="text/javascript">
    rec();
    function rec(){}
    var mymessage=confirm("你喜欢....");
    if(mymessage==true){
        document.write("xxx")
    }else {
        document.write("aaa")
    }
</script>
</html>
