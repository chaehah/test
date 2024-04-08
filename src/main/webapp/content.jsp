<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.input-file-button{
  background-color:#C0C0C0;
  border-radius: 4px;
  color: black;
  cursor: pointer;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
function changeImg(file){
	let formData = new FormData();
	console.log(file.files.length);
	console.log(file.files);
	console.log(file.files[0]);
	for(let i=0;i<file.files.length;i++){
		let file2 = file.files[i];
		formData.append("file2",file2);
	}
	
	$.ajax({
		url: "./content.jsp",
		type: "post",
		data: formData,
		processData: false,
		contentType: false,
		success: function(data){
			alert("success");
			let reader = new FileReader();
			reader.onload = function(e){
				$('#fileImg').attr("src", e.target.result);
				$("#filename").text(file.files[0].name);
			}
			reader.readAsDataURL(file.files[0]);
		}
	});
}
</script>
</head>
<body>
	<h2>${user.userName }회원정보</h2>
	<form action="./userUpdate.do" method="post" enctype="multipart/form-data">
		<table border="1">
			<tr>
				<td>회원번호</td>
				<td><input type="text" name="userNum" readonly value="${user.userNum }"></td>
				<!-- 이미지 파일 -->
				<td rowspan="7" align="center">
					<c:choose>
						<c:when test="${user.userFile eq null || user.userFile eq ''}">
							<img alt="" src="./uploads/basicPic.png" id="fileImg" width="200px" height="200px"><br/><br/>
							<input type="file" name="file" onchange="changeImg(this)">
						</c:when>
						<c:otherwise>
							<img alt="${user.userFile }" src="./uploads/${user.userFile }" id="fileImg" width="200px" height="200px"><br/><br/>
							<label class="input-file-button" for="input-file">
								수정
							</label>
							<input type="file" name="file" id="input-file" onchange="changeImg(this)" style="display: none;"/>
							<span id="filename">${user.userFile }</span>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>아이디</td>
				<td><input type="text" name="userId" readonly value="${user.userId }"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="text" name="userPw" value="${user.userPw }"></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" name="userName" value="${user.userName }"></td>
			</tr>
			<tr>
				<td>나이</td>
				<td><input type="text" name="userAge"value="${user.userAge }"></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><input type="text" name="userEmail" value="${user.userEmail }">
			</tr>
			<tr>
				<td>전화번호</td>
				<td><input type="text" name="userPhone" value="${user.userPhone }">
			</tr>
			<c:if test="${sessionScope.userId == user.userId  }">
			<tr>
				<td><input type="submit" value="정보수정"></td>
				<td><input type="reset" value="초기화"></td>
			</tr>
			</c:if>
		</table>
	</form>
	<button type="button" onclick="location.href='./list.do'">리스트로 돌아가기</button>
</body>
</html>