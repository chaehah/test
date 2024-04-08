<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
// [검색 요청] 버튼 클릭 시 실행할 메서드를 정의합니다.
$(function(){
	$('#searchBtn').click(function(){
		$.ajax({
			url : "./NaverSearchAPI.do",	// 요청 URL
			type : "get",					// HTTP 메서드
			data : {						// 매개변수로 전달할 데이터
				keyword : $('#keyword').val(),					// 검색어
				startNum : $('#startNum option:selected').val()	// 검색 시작 위치
			},
			dataType : "json",				// 응답 데이터 형식
			success : sucFuncJson,			// 요청 성공 시 호출할 메서드 설정
			error : errFunc					// 요청 실패 시 호출할 메서드 설정
		});
	});
});

// 검색 성공 시 결과를 화면에 뿌려줍니다.
function sucFuncJson(d){
	var str = "";
	str += "<table>";
	str += "<tr>";
	$.each(d.items, function(index, item){
		str += "<td width='25%'><a href='" + item.link + "' target='_blank'><img alt='"+item.title+"' src='" + item.image + "' width='200px' height='200px'></a>";
		str += "<h4>" + item.title + "</h4>";
		str += "최저가: " + item.lprice + "<br>";
		str += "분류: " + item.category3 + "<br>";
		if(index%4==3){
 			str+="</tr><tr>";
		}
	});
	str += "</tr>";
	str += "</table>";
	$('#searchResult').html(str);
}

// 실패 시 경고창을 띄워줍니다.
function errFunc(e){
	alert("실패: "+e.status);
}
</script>
</head>
<body>
<div>
	<div>
		<form id="searchFrm">
			<h2>네이버 쇼핑하기</h2> 
			<button type="button" onclick="location.href='./list.do'">리스트로 돌아가기</button>
			<br/>
			<select id="startNum">
				<option value="1">1페이지</option>
				<option value="11">2페이지</option>
				<option value="21">3페이지</option>
				<option value="31">4페이지</option>
				<option value="41">5페이지</option>
			</select>
			<input type="text" id="keyword" placeholder="검색어를 임력하세요." />
			<button type="button" id="searchBtn">검색</button>
		</form>
	</div>
	<div class="row" id="searchResult">
	</div>
</div>
</body>
</html>