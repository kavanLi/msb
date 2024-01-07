<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style type="text/css">
        #empTable{
            width: 80%;
            border: 1px solid blue;
            margin: 0px auto;
        }
        #empTable th,td{
            border: 1px solid green;
            text-align: center;
        }
    </style>
</head>
<body>
<table id="empTable" cellpadding="0px" cellspacing="0px">
    <tr>
        <th>索引</th>
        <th>工号</th>
        <th>姓名</th>
        <th>岗位</th>
        <th>上级工号</th>
        <th>入职日期</th>
        <th>入职时间</th>
        <th>入职日期时间</th>
        <th>入职日期时间</th>
        <th>薪资</th>
        <th>补助</th>
        <th>部门号</th>
    </tr>

    <#if empList??>
        <#list empList as emp>
            <tr <#if emp_index%2 == 0 >style="background-color:gray"</#if>>
                <td>${emp_index}</td>
                <td>${emp.empno}</td>
                <td <#if emp.ename == 'KING'>style="color: aqua"</#if>>${emp.ename}</td>
                <td>${emp.job}</td>
                <td>${emp.mgr!'无'}</td>
                <td>${emp.hiredate?date}</td>
                <td>${emp.hiredate?time}</td>
                <td>${emp.hiredate?datetime}</td>
                <td>${emp.hiredate?string("yyyy年MM月dd日 HH:mm:ss")}</td>
                <td <#if emp.sal gte 2000>style="color: red"</#if>>${emp.sal}</td>
                <td>${emp.comm!'0'}</td>
                <td>${emp.deptno}</td>
            </tr>
        </#list>
    </#if>

</table>


<#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
<#assign data=text?eval />
开户行：${data.bank} 账号：${data.account}


</body>
</html>