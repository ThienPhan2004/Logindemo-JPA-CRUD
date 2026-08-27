<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sửa Video</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/admin/videos">&laquo; Danh sách Video</a>
    <h2>Sửa Video</h2>

    <form action="<c:url value="/admin/video/update"/>" method="post" enctype="multipart/form-data">
        <input type="hidden" name="videoId" value="${video.videoId}">
        <input type="hidden" name="poster" value="${video.poster}">

        <label for="title">Tiêu đề:</label><br>
        <input type="text" id="title" name="title" value="${video.title}" required><br><br>

        <label for="description">Mô tả:</label><br>
        <textarea id="description" name="description" rows="4" cols="50">${video.description}</textarea><br><br>

        <label for="categoryId">Category:</label><br>
        <select id="categoryId" name="categoryId" required>
            <c:forEach items="${listcate}" var="c">
                <option value="${c.categoryId}" ${c.categoryId == video.category.categoryId ? 'selected' : ''}>
                    ${c.categoryname}
                </option>
            </c:forEach>
        </select><br><br>

        <c:choose>
            <c:when test="${video.poster != null && video.poster.length() >= 5 && video.poster.substring(0,5) == 'https'}">
                <c:url value="${video.poster}" var="imgUrl"/>
            </c:when>
            <c:otherwise>
                <c:url value="/image?fname=${video.poster}" var="imgUrl"/>
            </c:otherwise>
        </c:choose>
        <img height="120" width="180" src="${imgUrl}" alt="poster"/><br><br>

        <label for="poster1">Upload poster mới (nếu muốn thay):</label><br>
        <input type="file" id="poster1" name="poster1"><br><br>

        <label>
            <input type="checkbox" name="active" ${video.active ? 'checked' : ''}> Kích hoạt (hiển thị công khai)
        </label><br><br>

        <input type="submit" value="Cập nhật">
    </form>
</body>
</html>
