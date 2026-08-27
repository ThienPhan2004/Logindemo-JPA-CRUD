<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Video</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/admin/videos">&laquo; Danh sách Video</a>
    <h2>Thêm Video mới</h2>

    <form action="<c:url value="/admin/video/insert"/>" method="post" enctype="multipart/form-data">
        <label for="title">Tiêu đề:</label><br>
        <input type="text" id="title" name="title" required><br><br>

        <label for="description">Mô tả:</label><br>
        <textarea id="description" name="description" rows="4" cols="50"></textarea><br><br>

        <label for="categoryId">Category:</label><br>
        <select id="categoryId" name="categoryId" required>
            <c:forEach items="${listcate}" var="c">
                <option value="${c.categoryId}">${c.categoryname}</option>
            </c:forEach>
        </select><br><br>

        <label for="poster1">Poster (upload từ máy):</label><br>
        <input type="file" id="poster1" name="poster1"><br><br>

        <label>
            <input type="checkbox" name="active" checked> Kích hoạt (hiển thị công khai)
        </label><br><br>

        <input type="submit" value="Thêm mới">
    </form>
</body>
</html>
