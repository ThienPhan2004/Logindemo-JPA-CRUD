<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Category</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/admin/categories">&laquo; Danh sách Category</a>
    <h2>Thêm Category mới</h2>

    <form action="<c:url value="/admin/category/insert"/>" method="post" enctype="multipart/form-data">
        <label for="categoryname">Tên Category:</label><br>
        <input type="text" id="categoryname" name="categoryname" required><br><br>

        <label for="images">Link ảnh (nếu có):</label><br>
        <input type="text" id="images" name="images"><br><br>

        <label for="images1">Hoặc upload ảnh từ máy:</label><br>
        <input type="file" id="images1" name="images1"><br><br>

        <label>Trạng thái:</label><br>
        <input type="radio" id="ston" name="status" value="1" checked>
        <label for="ston">Hoạt động</label>
        <input type="radio" id="stoff" name="status" value="0">
        <label for="stoff">Khóa</label>
        <br><br>

        <input type="submit" value="Thêm mới">
    </form>
</body>
</html>
