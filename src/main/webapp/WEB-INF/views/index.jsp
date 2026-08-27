<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ</title>
</head>
<body>
    <h1>Chào mừng, ${account.fullName != null ? account.fullName : account.userName}!</h1>
    <p>Bạn đã đăng nhập thành công vào hệ thống.</p>

    <c:if test="${account.roleid == 1}">
        <h3>Khu vực quản trị</h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/categories">Quản lý Category</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/videos">Quản lý Video</a></li>
        </ul>
    </c:if>

    <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
</body>
</html>
