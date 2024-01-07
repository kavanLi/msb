
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="getDataByPojo" method="post">
    <p>姓名<input type="text" name="pname"></p>
    <p>年龄<input type="text" name="page"></p>
    <p>性别:
        <input type="radio" name="gender" value="1" >男
        <input type="radio" name="gender" value="0" >女
    </p>
    <p>爱好
        <input type="checkbox" name="hobby" value="1"> 篮球
        <input type="checkbox" name="hobby" value="2"> 足球
        <input type="checkbox" name="hobby" value="3"> 羽毛球
    </p>生日
    <p>
        <input type="text" name="birthdate">
    </p>
    宠物:
    <p>
        宠物: 名字:<input type="text" name="pet.petName" >类型:<input type="text" name="pet.petType">
    </p>
    宠物List:
    <p>
        宠物1: 名字:<input type="text" name="pets[0].petName" >类型:<input type="text" name="pets[0].petType">
    </p>
    <p>
        宠物2: 名字:<input type="text" name="pets[1].petName" >类型:<input type="text" name="pets[1].petType">
    </p>
    宠物Map:
    <p>
        宠物1: 名字:<input type="text" name="petMap['a'].petName" >类型:<input type="text" name="petMap['a'].petType">
    </p>
    <p>
        宠物2: 名字:<input type="text" name="petMap['b'].petName" >类型:<input type="text" name="petMap['b'].petType">
    </p>
    <input type="submit">
</form>



</body>
</html>
