<%@page import="kr.co.jsp.board.model.Board"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.jsp.board.model.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	// BoardDAO 클래스의 selectAll() 메서드를 호출하여 DB에 들어있는 모든 글들을 리스트로 반환.
    	List<Board> articles = BoardDAO.getInstance().selectAll();    	
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
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
	<% if(articles.size() > 0) { %>
	<h1>게시판 목록</h1>
	<br>
	<table border="1">
		<tr>
			<th>번호</th>
			<th>작성자</th>
			<th>제목</th>
			<th>비고</th>
		</tr>
		<!-- selectAll() 결과값으로 리턴받은 리스트를 반복문을 사용하여 하나씩 테이블에 출력하면 된다. -->
		<% for(Board b : articles) {%>
		<tr>
			<td><%= b.getBoardId() %></td>
			<td><%= b.getWriter() %></td>
			<td>
				<a href="content.jsp?id=<%=b.getBoardId()%>"> <%= b.getTitle() %></a>	
			</td>
			<td><a class="del-btn" href="delete.jsp?id=<%= b.getBoardId()%>">삭제</a></td>
		</tr>
		<% } %>
	</table>
	<!-- DB에서 얻어온 글이 하나도 없을 때는 '게시물이 존재하지 않습니다.' 문장을 브라우저에 띄우면 된다. -->
	<% } else { %>
		<h2>게시물이 존재하지 않습니다.</h2>
	<% } %>
	<br>
	<a href="write.jsp">게시글 작성하기</a>
</body>
</html>