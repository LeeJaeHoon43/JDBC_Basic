<%@page import="kr.co.jsp.board.model.BoardDAO"%>
<%@page import="kr.co.jsp.board.model.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%
		/*
			- 필요한 파라미터 값을 불러와서 그것을 토대로 Board 객체를 생성.
			- BoardDAO의 update()를 이용하여 수정한 객체를 DB에 넣고 결과가 성공이라면 해당 글 상세보기 페이지로 이동.(sendRedirect)
			- 결과가 실패라면, list.jsp로 리다이렉팅 한다.
		*/
		request.setCharacterEncoding("utf-8");
	
		long id = Long.parseLong(request.getParameter("boardNo"));
		
		Board article = new Board();
	
		article.setBoardId(id);
		article.setWriter(request.getParameter("writer"));
    	article.setTitle(request.getParameter("title"));
    	article.setContent(request.getParameter("content"));
    	
    	if(BoardDAO.getInstance().update(article)) {
			response.sendRedirect("content.jsp?id=" + id);
		}else {
			response.sendRedirect("list.jsp");
		}
	%>