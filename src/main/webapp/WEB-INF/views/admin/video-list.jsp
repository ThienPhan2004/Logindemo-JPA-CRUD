<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Video</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/home">&laquo; Trang chủ</a>
    <h2>Danh sách Video</h2>

    <a href="<c:url value="/admin/video/add"/>">+ Thêm Video</a>
    <br><br>

    <form action="${pageContext.request.contextPath}/admin/videos" method="get">
        <input type="text" name="keyword" placeholder="Tìm theo tiêu đề video" value="${keyword}">
        <button type="submit">Tìm kiếm</button>
    </form>
    <hr>

    <table border="1" width="100%" cellpadding="6">
        <tr>
            <th>STT</th>
            <th>Poster</th>
            <th>Tiêu đề</th>
            <th>Category</th>
            <th>Lượt xem</th>
            <th>Active</th>
            <th>Hành động</th>
        </tr>
        <c:forEach items="${listvideo}" var="v" varStatus="stt">
            <tr>
                <td>${stt.index + 1}</td>
                <c:choose>
                    <c:when test="${v.poster != null && v.poster.length() >= 5 && v.poster.substring(0,5) == 'https'}">
                        <c:url value="${v.poster}" var="imgUrl"/>
                    </c:when>
                    <c:otherwise>
                        <c:url value="/image?fname=${v.poster}" var="imgUrl"/>
                    </c:otherwise>
                </c:choose>
                <td><img height="80" width="120" src="${imgUrl}" alt="${v.title}"/></td>
                <td>${v.title}</td>
                <td>${v.category.categoryname}</td>
                <td>${v.views}</td>
                <td>${v.active ? 'Có' : 'Không'}</td>
                <td>
                    <a href="<c:url value='/admin/video/edit?id=${v.videoId}'/>">Sửa</a>
                    |
                    <a href="<c:url value='/admin/video/delete?id=${v.videoId}'/>"
                       onclick="return confirm('Bạn chắc chắn muốn xóa?');">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty listvideo}">
            <tr><td colspan="7">Không có dữ liệu</td></tr>
        </c:if>
    </table>

    <c:if test="${empty keyword && totalPages > 1}">
        <br>
        <c:forEach begin="1" end="${totalPages}" var="p">
            <c:choose>
                <c:when test="${p == currentPage}">
                    <b>${p}</b>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/admin/videos?page=${p}">${p}</a>
                </c:otherwise>
            </c:choose>
            &nbsp;
        </c:forEach>
    </c:if>
</body>
</html>
