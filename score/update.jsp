<%@page import="java.util.List"%>
<%@page import="kr.co.jsp.score.model.ScoreDAO"%>
<%@page import="kr.co.jsp.score.model.ScoreVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    	long id = Long.parseLong(request.getParameter("id"));
    	List<ScoreVO> scoreList = ScoreDAO.getInstance().search_Num(id);
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>시험 점수 수정하기</h1>
	<% for(ScoreVO s : scoreList){ %>
	<h2><%= s.getName() %>님 시험 점수 수정</h2>
	<form action="update_controller.jsp" method="post">
		<input type="hidden" name="id" value="<%=s.getId()%>">
		<p>
			# 국어 : <input type="text" name="u_kor" maxlength="3"> <br> 
			# 영어 : <input type="text" name="u_eng" maxlength="3"> <br> 
			# 수학 : <input type="text" name="u_math" maxlength="3"> <br>
			<button type="submit">수정</button> 			
		</p>
	<% } %>
	</form>
	<br>
	<a href="score_list.jsp">목록 보기</a>
</body>
</html>