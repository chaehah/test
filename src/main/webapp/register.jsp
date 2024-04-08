<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
		url: "./register.jsp",
		type: "post",
		data: formData,
		processData: false,
		contentType: false,
		success: function(data){
			alert("success");
			let reader = new FileReader();
			reader.onload = function(e){
				$('#fileImg').attr("src", e.target.result);
			}
			reader.readAsDataURL(file.files[0]);
		}
	});
}
function idCheck(){
	let userId = $('input[name=userId]').val();
	console.log("입력한 id값:"+userId);
	
	$.ajax({
		url: "idCheck.do",
		type: "post",
		data: {
				userId : userId
		},
	    dataType: 'json',
		success: function(resData){
			if(resData.result>0){
				console.log(resData.result)
				alert("중복된 아이디입니다.")
			}else{
				console.log(resData.result)
				alert("사용가능한 아이디입니다.")
			}
		},
		error:function(request, status, error){
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}
</script>
</head>
<body>
	<h3>회원가입</h3>
	<form action="./userJoin.do" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>아이디</td>
				<td><input type="text" name="userId"><button type="button" onclick="idCheck()">아이디중복체크</button>
				<td rowspan="7" align="center">
					<img alt="" src="./uploads/basicPic.png" id="fileImg" width="200px" height="200px"><br/><br/>
					<input type="file" name="file" onchange="changeImg(this)">
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="userPw">
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" name="userName">
			</tr>
			<tr>
				<td>나이</td>
				<td><input type="text" name="userAge">
			</tr>
			<tr>
				<td>이메일</td>
				<td><input type="text" name="userEmail">
			</tr>
			<tr>
				<td>전화번호</td>
				<td><input type="text" name="userPhone">
			</tr>
			<tr>
				<td><input type="submit" value="회원가입"></td>
			</tr>
		</table>
	</form>
	<button type="button" onclick="location.href='./list.do'">리스트로 돌아가기</button>

	<a href="./LoginForm.jsp">로그인 바로가기</a>
</body>
</html>