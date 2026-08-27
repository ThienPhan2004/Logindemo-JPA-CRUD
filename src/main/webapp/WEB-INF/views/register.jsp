<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng Ký Tài Khoản</title>
</head>
<body>
    <h2>Tạo tài khoản mới</h2>

    <c:if test="${alert != null}">
        <p style="color:red;">${alert}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post">
        <div>
            <label for="username">Tài khoản:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <br>
        <div>
            <label for="fullname">Họ tên:</label>
            <input type="text" id="fullname" name="fullname">
        </div>
        <br>
        <div>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <br>
        <div>
            <label for="phone">Số điện thoại:</label>
            <input type="text" id="phone" name="phone">
        </div>
        <br>
        <div>
            <label for="password">Mật khẩu:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <br>
        <div>
            <label for="confirmPassword">Nhập lại mật khẩu:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>
        <br>
        <button type="submit">Tạo tài khoản</button>
    </form>

    <p>Đã có tài khoản?
        <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
    </p>
</body>
</html>
