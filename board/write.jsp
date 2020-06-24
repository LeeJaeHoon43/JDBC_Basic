<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>게시글 등록</h2>
	<form action="write_con.jsp" method="post">
		<p>
			# 작성자 : <input type="text" name="writer"> <br><br>
			# 제목 : <input type="text" name="title"> <br><br>
			# 내용 <br>
			<textarea cols="45" rows="15" name="content"></textarea> <br>
			<input type="submit" value="확인">
		</p>
	</form>
</body>
</html>