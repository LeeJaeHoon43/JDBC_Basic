<%@page import="kr.co.jsp.score.model.ScoreDAO"%>
<%@page import="kr.co.jsp.score.model.ScoreVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
    	String keyword = "%" + request.getParameter("keyword") + "%";
 
    	List<ScoreVO> scoreList = ScoreDAO.getInstance().search(keyword);
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
</style>
</head>
<body>
	<% if(scoreList.size() != 0) { %>
	<h1>검색한 학생의 성적 조회</h1>
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
	<% } else { %>
		<h2>검색 결과가 없습니다.</h2>
	<% } %>
	<br>
	<a href="insert_form.jsp">다른 점수 등록하기</a>
	<a href="score_list.jsp">목록으로 돌아가기</a>
</body>
</html>