<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Category</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/home">&laquo; Trang chủ</a>
    <h2>Danh sách Category</h2>

    <a href="<c:url value="/admin/category/add"/>">+ Thêm Category</a>
    <br><br>

    <form action="${pageContext.request.contextPath}/admin/categories" method="get">
        <input type="text" name="keyword" placeholder="Tìm theo tên category" value="${keyword}">
        <button type="submit">Tìm kiếm</button>
    </form>
    <hr>

    <table border="1" width="100%" cellpadding="6">
        <tr>
            <th>STT</th>
            <th>Ảnh</th>
            <th>Tên Category</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
        <c:forEach items="${listcate}" var="cate" varStatus="stt">
            <tr>
                <td>${stt.index + 1}</td>
                <c:choose>
                    <c:when test="${cate.images != null && cate.images.length() >= 5 && cate.images.substring(0,5) == 'https'}">
                        <c:url value="${cate.images}" var="imgUrl"/>
                    </c:when>
                    <c:otherwise>
                        <c:url value="/image?fname=${cate.images}" var="imgUrl"/>
                    </c:otherwise>
                </c:choose>
                <td><img height="80" width="120" src="${imgUrl}" alt="${cate.categoryname}"/></td>
                <td>${cate.categoryname}</td>
                <td>
                    <c:choose>
                        <c:when test="${cate.status == 1}">Hoạt động</c:when>
                        <c:otherwise>Khóa</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a href="<c:url value='/admin/category/edit?id=${cate.categoryId}'/>">Sửa</a>
                    |
                    <a href="<c:url value='/admin/category/delete?id=${cate.categoryId}'/>"
                       onclick="return confirm('Bạn chắc chắn muốn xóa?');">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty listcate}">
            <tr><td colspan="5">Không có dữ liệu</td></tr>
        </c:if>
    </table>

    <!-- Phan trang -->
    <c:if test="${empty keyword && totalPages > 1}">
        <br>
        <c:forEach begin="1" end="${totalPages}" var="p">
            <c:choose>
                <c:when test="${p == currentPage}">
                    <b>${p}</b>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/admin/categories?page=${p}">${p}</a>
                </c:otherwise>
            </c:choose>
            &nbsp;
        </c:forEach>
    </c:if>
</body>
</html>
