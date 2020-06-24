<%@page import="kr.co.jsp.score.model.ScoreVO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.jsp.score.model.ScoreDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% 
    	List<ScoreVO> scoreList = ScoreDAO.getInstance().list();
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.upd-btn{
	display: inline-block;
	text-decoration: none;
	color: white;
	background: blue;
	border: 1px solid black;
}
.upd-btn:hover {
	background: red;
}
.del-btn{
	display: inline-block;
	text-decoration: none;
	color: white;
	background: red;
	border: 1px solid black;
}
.del-btn:hover {
	background: blue;
}
input[name=keyword]{
	display: inline-block;
	width: 130px;
	padding: 5px 0;
	border: 1px solid gray;
}
input[name=keyword]:focus {
	width: 200px;
	transition: 0.5s;
}
</style>
</head>
<body>
	<h1>학생들의 전체 성적 조회</h1>
	<form action="search.jsp">
		검색 <input type="text" name="keyword" placeholder="검색할 이름 입력">
		<button type="submit">확인</button>
	</form>
	<br>
	<table border="1">
		<tr>
			<th>이름</th>
			<th>국어</th>
			<th>영어</th>
			<th>수학</th>
			<th>총점</th>
			<th>평균</th>
			<th colspan="2">비고</th>
		</tr>
		<% for(ScoreVO s : scoreList) {%>
		<tr>
			<td><%= s.getName() %></td>
			<td><%= s.getKor() %></td>
			<td><%= s.getEng() %></td>
			<td><%= s.getMath() %></td>
			<td><%= s.getTotal() %></td>
			<td><%= s.getAverage() %></td>
			<td><a class="upd-btn" href="update.jsp?id=<%= s.getId() %>">수정</a></td>
			<td><a class="del-btn" href="delete.jsp?id=<%= s.getId() %>">삭제</a></td>
		</tr>
		<% } %>
	</table>
	<br>
	<a href="insert_form.jsp">새로운 점수 등록하기</a>
</body>
</html>