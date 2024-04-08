<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://kit.fontawesome.com/bac523960b.js" crossorigin="anonymous"></script>
</head>
<body>
	<h2>회원리스트</h2>
	<table border="1">
		<thead>
			<tr>
				<th>회원번호</th><th>아이디</th><th>비밀번호</th><th>이름</th><th>나이</th><th>이메일</th><th>전화번호</th><th>삭제</th>
			</tr>
		</thead>
		<c:forEach var="user" items="${userList}" varStatus="idx" begin="${start }" end="${end }">
			<tr>
				<td>${user.userNum }</td><td><a href="content.do?userNum=${user.userNum }">${user.userId }</a></td><td>${user.userPw }</td><td>${user.userName }</td><td>${user.userAge }</td><td>${user.userEmail }</td><td>${user.userPhone }</td>
				<td><button type="button" onclick="location.href='<%=request.getContextPath()%>/delete.do?userNum=${user.userNum }'">삭제</button></td>
			</tr>
		</c:forEach>
	</table>
	<div>
		<!-- 1번 페이지로 이동 활성/비활성 -->
		<c:choose>
			<c:when test="${ currentPage == 1 }">
				<a><i class="fa-solid fa-angles-left"></i></a>
			</c:when>
			<c:otherwise>
				<a href="./list.do?pageNum=1"><i class="fa-solid fa-angles-left"></i></a>
			</c:otherwise>
		</c:choose>
		<!-- 이전 버튼 활성/비활성 -->
		<c:choose>
			<c:when test="${startPage>pageCnt }">
				<a href="./list.do?pageNum=${startPage-pageCnt }"><i class="fa-solid fa-angle-left"></i></a>
			</c:when>
			<c:otherwise>
				<a><i class="fa-solid fa-angle-left"></i></a>
			</c:otherwise>
		</c:choose>
		<!-- 페이지 반복문 -->
		<c:forEach var="page" begin="${startPage }" end="${endPage}" >
			<c:choose>
				<c:when test="${page == currentPage }">
					<strong><a href="./list.do?pageNum=${page }">${page }</a>&nbsp;</strong>
				</c:when>
				<c:otherwise>
					<a href="./list.do?pageNum=${page }" style="text-decoration: none;">${page }</a>&nbsp;
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<!-- 다음 버튼 활성/비활성 -->
		<c:choose>
			<c:when test="${endPage<pageSize }">
				<a href="./list.do?pageNum=${startPage+pageCnt }"><i class="fa-solid fa-angle-right"></i></a>
			</c:when>
			<c:otherwise>
				<a><i class="fa-solid fa-angle-right"></i></a>
			</c:otherwise>
		</c:choose>
		<!-- 마지막 페이지로 이동 활성/비활성 -->
		<c:choose>
			<c:when test="${ currentPage == pageSize }">
				<a><i class="fa-solid fa-angles-right"></i></a>
			</c:when>
			<c:otherwise>
				<a href="./list.do?pageNum=${pageSize}"><i class="fa-solid fa-angles-right"></i></a>
			</c:otherwise>
		</c:choose>
		
	</div>
	<br/>
	<button onclick="location.href='./register.jsp'">회원가입</button>
	
	<c:if test="${sessionScope.userId != null }">
		<h3>로그인 성공</h3>
		<button onclick="location.href='./logout.do'">로그아웃</button>
	</c:if>
	<a href="./SearchView.jsp">네이버 쇼핑 바로가기</a>
</body>
</html>