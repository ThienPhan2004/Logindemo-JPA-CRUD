<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<head>
    <title>Cập nhật Profile</title>
</head>
<body>
    <h2>Thông tin cá nhân</h2>
    
    <c:if test="${not empty message}">
        <p style="color: green;">${message}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/profile" method="post" enctype="multipart/form-data">
        <label>Họ và tên:</label>
        <input type="text" name="fullname" value="${account.fullName}" required /><br/><br/>

        <label>Số điện thoại:</label>
        <input type="text" name="phone" value="${account.phone}" /><br/><br/>

        <label>Ảnh đại diện hiện tại:</label><br/>
        <c:if test="${not empty account.images}">
            <img src="${pageContext.request.contextPath}/image?fname=${account.images}" width="150" alt="Avatar"/><br/>
        </c:if>
        <c:if test="${empty account.images}">
            <p>Chưa có ảnh</p>
        </c:if>
        <br/>

        <label>Chọn ảnh mới:</label>
        <input type="file" name="images" accept="image/*" /><br/><br/>

        <input type="submit" value="Cập nhật" />
    </form>
</body>