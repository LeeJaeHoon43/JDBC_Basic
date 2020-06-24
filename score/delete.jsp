<%@page import="kr.co.jsp.score.model.ScoreDAO"%>
<%@page import="kr.co.jsp.score.model.ScoreVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
		// 파라미터 데이터 얻어온 후 DAO에게 삭제 요청을 하면 된다.(주소값 얻어오고 메서드 호출.)
		// 삭제가 완료되면 score_list.jsp로 강제 이동.
		// 삭제가 실패해도  score_list.jsp로 강제 이동.
				
		long id = Long.parseLong(request.getParameter("id"));		

		// ScoreDAO dao = ScoreDAO.getInstance();
		// boolean flag = dao.delete(id);

		if(ScoreDAO.getInstance().delete(id)) {
			response.sendRedirect("score_list.jsp");
		}else {
			response.sendRedirect("score_list.jsp");
		}
	%>