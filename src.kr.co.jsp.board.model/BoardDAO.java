package kr.co.jsp.board.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BoardDAO implements IBoardDAO {
	
	DataSource ds; // 커넥션 풀 사용 : 데이터 소스 객체 생성.
	// 싱글톤 디자인 패턴 (객체 생성을 단 하나로 제한).
	// 1. 클래스 외부에서 객체를 생성할 수 없도록 생성자에 private제한을 붙인다.
	private BoardDAO() {
		// 커넥션 풀 연결 방법.
		// InitialContext 객체 생성 -> 저 객체에 context.xml에 작성한 설정 파일이 저장됨.
		try {
			InitialContext ct = new InitialContext(); // 이니셜컨텍스트 객체 생성.
			ds = (DataSource) ct.lookup("java:comp/env/jdbc/mysql"); // 이니셜컨텍스트로부터 찾아서 데이터 소스로 형 변환 후 저장.
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// 2. 클래스 내부에서 자신 스스로의 객체를 단 1개만 생성.
	private static BoardDAO boardDAO = new BoardDAO();

	// 3. 외부에서 객체 요구 시 공개된 메서드를 통해 주소값 리턴.
	public static BoardDAO getInstance() {
		if(boardDAO == null) {
			boardDAO = new BoardDAO();
		}
		return boardDAO;
	}

	// 커넥션 객체를 생성하여 제공(리턴)해주는 유틸 메서드 선언.
	/*
	private Connection getConnection() throws Exception {
		String url = "jdbc:mysql://localhost:3306/jsp_practice?serverTimezone=Asia/Seoul";
		String uid = "jsp";
		String upw = "jsp";

		return DriverManager.getConnection(url, uid, upw);
	}
	*/
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 게시글 쓰기 메서드.
	@Override
	public boolean insert(Board article) {

		boolean flag = false;

		String sql = "INSERT INTO board (writer, title, content)" + " VALUES(?,?,?)";

		try {
			// conn = getConnection();
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, article.getWriter());
			pstmt.setString(2, article.getTitle());
			pstmt.setString(3, article.getContent());

			if (pstmt.executeUpdate() == 1) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return flag;
	}

	// 게시글 전체 조회 메서드.
	@Override
	public List<Board> selectAll() {

		List<Board> articles = new ArrayList<>();

		String sql = "SELECT * FROM board ORDER BY board_id DESC";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Board article = new Board(
						rs.getLong("board_id"),
						rs.getString("writer"),
						rs.getString("title"),
						rs.getString("content"),
						rs.getDate("create_At")
						);
				articles.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return articles;
	}
	
	// 게시글 상세 조회 메서드.
	@Override
	public Board selectOne(long boardId) {

		Board article  = null;

		String sql = "SELECT * FROM board WHERE board_id=?";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boardId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				article = new Board(
						rs.getLong("board_id"),
						rs.getString("writer"),
						rs.getString("title"),
						rs.getString("content"),
						rs.getDate("create_At")
						);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return article;
	}
	
	// 게시글 수정 메서드.
	@Override
	public boolean update(Board article) {
		
		boolean flag = false;
		
		String sql = "UPDATE board SET writer=?, title=?, content=? WHERE board_id=?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, article.getWriter());
			pstmt.setString(2, article.getTitle());
			pstmt.setString(3, article.getContent());
			pstmt.setLong(4, article.getBoardId());
			
			if (pstmt.executeUpdate() == 1) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return flag;
	}

	// 게시글 삭제 메서드.
	@Override
	public boolean delete(long boardId) {
		
		boolean flag = false;
		
		String sql = "DELETE FROM board WHERE board_id=?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boardId);
			
			if(pstmt.executeUpdate() == 1) {
				flag = true;
			}else {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return flag;
	}
}
