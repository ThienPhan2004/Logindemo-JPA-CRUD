<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><sitemesh:write property="title"/></title>
    <!-- Thêm CSS/JS dùng chung (Bootstrap, FontAwesome...) ở đây -->
</head>
<body>
    <!-- Nhúng Header chung của trang web -->
    <div>
        <%@ include file="/commons/web/header.jsp"%>
    </div>

    <!-- Vị trí nội dung của các trang con (như profile.jsp) sẽ được thay thế vào đây[cite: 2] -->
    <div>
        <sitemesh:write property="body"/>
    </div>

    <!-- Nhúng Footer chung của trang web[cite: 2] -->
    <div>
        <%@ include file="/commons/web/footer.jsp"%>
    </div>
</body>
</html>