<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>曲目列表-freemarker</title>
</head>
<table border="1">
    <thead>
    </thead>
    <tbody>
    <#list playList as e>
        <tr>
            <td>${e.id}</td>
            <td>${e.name}</td>
            <td>${e.artist}</td>
            <td>${e.album}</td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>
```