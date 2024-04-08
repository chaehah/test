<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>로그인</h3>
	<form action="userLogin.do" method="post">
		<table>
			<tr>
				<td>아이디</td>
				<td><input type="text" name="userId">
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="userPw">
			</tr>
			<tr>
				<td><input type="submit" value="로그인"></td>
			</tr>
		</table>
	</form>
	
	<a href="./register.jsp">회원가입 가기</a>
	<a href="./newLogin.jsp">새로운 로그인 가기</a>


</body>
</html>