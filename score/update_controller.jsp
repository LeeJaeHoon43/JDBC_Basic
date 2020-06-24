<%@page import="kr.co.jsp.score.model.ScoreDAO"%>
<%@page import="kr.co.jsp.score.model.ScoreVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    	request.setCharacterEncoding("utf-8");
    
    	long id = Long.parseLong(request.getParameter("id"));
    
    	ScoreVO scores = new ScoreVO();
    	
    	scores.setId(Long.parseLong(request.getParameter("id")));
    	scores.setKor(Integer.parseInt(request.getParameter("u_kor")));
		scores.setEng(Integer.parseInt(request.getParameter("u_eng")));
		scores.setMath(Integer.parseInt(request.getParameter("u_math")));
		scores.setTotal();
		scores.setAverage();
		
		if(ScoreDAO.getInstance().update(scores)){
			response.sendRedirect("score_list.jsp");
		}else{
			response.sendRedirect("score_list.jsp");
		}
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>