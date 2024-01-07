
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery.min.js"></script>
    <script>
        /*
        * 1 键值对形式get  数据量小
        * 2 JSON格式对象
        * */


        $(function(){

           var jsonObj= {ename:"张三",hiredate:"2022-2-2"}
           var jsonStr =JSON.stringify(jsonObj)
            $.ajax({
                url:"demo1.action",
                data:jsonStr,
                type:"post",
                contentType:"application/json",
                success:function(result){
                    console.log(result)
                }
            })


        })
    </script>
</head>
<body>

</body>
</html>
