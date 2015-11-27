<%--
  Created by IntelliJ IDEA.
  User: liuxh
  Date: 15-10-24
  Time: 下午3:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/normalize/3.0.3/normalize.min.css" rel="stylesheet">
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        var _appctx = '${pageContext.request.contextPath}';
    </script>
</head>
<body>
<div class="container-fluid">
    <c:choose>
        <c:when test="${preUrl != '#'}"><a href="${pageContext.request.contextPath}/user/list.html?${preUrl}">上一页</a></c:when>
    </c:choose>
    <c:choose>
        <c:when test="${postUrl != '#'}"><a href="${pageContext.request.contextPath}/user/list.html?${postUrl}">下一页</a></c:when>
    </c:choose>
    <table class="table table-bordered table-striped table-condensed">
        <thead>
        <tr>
            <th>头像</th>
            <th>openid</th>
            <th>昵称</th>
            <th>性别</th>
            <th>城市</th>
            <th>国家</th>
            <th>省份</th>
            <th>语言</th>
            <th>关注时间</th>
            <th>分组ID</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="member" items="${list}" varStatus="contentStatus">
            <tr>
                <td><img src="${member.headimgurl}" style="width: 80px;"></td>
                <td>${member.openid}</td>
                <td>${member.nickname}</td>
                <td><c:choose>
                    <c:when test="${member.sex=='1'}">男</c:when>
                    <c:when test="${member.sex=='2'}">女</c:when>
                    <c:otherwise>未知</c:otherwise>
                </c:choose></td>
                <td>${member.city}</td>
                <td>${member.country}</td>
                <td>${member.province}</td>
                <td>${member.language}</td>
                <td>${member.subscribe_time}</td>
                <td>${member.groupid}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
