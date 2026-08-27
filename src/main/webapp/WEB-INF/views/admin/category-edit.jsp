<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sửa Category</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/admin/categories">&laquo; Danh sách Category</a>
    <h2>Sửa Category</h2>

    <form action="<c:url value="/admin/category/update"/>" method="post" enctype="multipart/form-data">
        <input type="hidden" name="categoryId" value="${cate.categoryId}">

        <label for="categoryname">Tên Category:</label><br>
        <input type="text" id="categoryname" name="categoryname" value="${cate.categoryname}" required><br><br>

        <label for="images">Link ảnh (nếu có):</label><br>
        <input type="text" id="images" name="images" value="${cate.images}"><br><br>

        <c:choose>
            <c:when test="${cate.images != null && cate.images.length() >= 5 && cate.images.substring(0,5) == 'https'}">
                <c:url value="${cate.images}" var="imgUrl"/>
            </c:when>
            <c:otherwise>
                <c:url value="/image?fname=${cate.images}" var="imgUrl"/>
            </c:otherwise>
        </c:choose>
        <img height="120" width="180" src="${imgUrl}" alt="poster"/><br><br>

        <label for="images1">Upload ảnh mới (nếu muốn thay):</label><br>
        <input type="file" id="images1" name="images1"><br><br>

        <label>Trạng thái:</label><br>
        <input type="radio" id="ston" name="status" value="1" ${cate.status == 1 ? 'checked' : ''}>
        <label for="ston">Hoạt động</label>
        <input type="radio" id="stoff" name="status" value="0" ${cate.status != 1 ? 'checked' : ''}>
        <label for="stoff">Khóa</label>
        <br><br>

        <input type="submit" value="Cập nhật">
    </form>
</body>
</html>
