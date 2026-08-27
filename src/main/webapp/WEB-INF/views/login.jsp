<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng Nhập</title>
</head>
<body>
    <h2>Form Đăng Nhập System</h2>

    <c:if test="${alert != null}">
        <p style="color:red;">${alert}</p>
    </c:if>

    <!-- Form gui du lieu theo phuong thuc POST den Servlet /login -->
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <br>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <br>
        <button type="submit">Đăng Nhập</button>
    </form>

    <p>Chưa có tài khoản?
        <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a>
    </p>
</body>
</html>
