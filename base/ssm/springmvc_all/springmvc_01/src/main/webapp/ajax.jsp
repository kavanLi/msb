
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="js/jquery.min.js"></script>
    <script>
        $(function(){
            $("#btn").click(function(){
                $.get("testAjax",{pname:"晓明",page:"10"},function(data){
                    console.log(data.petName)
                    console.log(data.petType)
                })
            })
        })
    </script>
</head>
<body>
<input id="btn" type="button" value="testJSON">
</body>
</html>