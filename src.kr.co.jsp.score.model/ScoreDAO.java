package kr.co.jsp.score.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// DAO는 웹 서버의 DB연동 요청이 발생할 때마다 DataBase CRUD(CREATE, READ, UPDATE, DELETE)작업을 전담하는 클래스이다.

public class ScoreDAO implements IScoreDAO {

	// 싱글톤 디자인 패턴 (객체 생성을 단 하나로 제한하기 위한 방법)
	// 1. 클래스 외부에서 객체를 생성할 수 없도록 생성자에 private제한을 붙인다.
	private ScoreDAO() {
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC 커넥터 드라이버 호출.
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 2. 자신의 클래스 내부에서 스스로의 객체를 단 1개만 생성한다. (private static 붙이기)
	private static ScoreDAO scoreDAO = new ScoreDAO();

	// 3. 외부에서 객체를 요구할 시 공개된 메서드를 통해 위의 2번에서 미리 만들어둔 단 하나의 객체의 주소값을 리턴.
	public static ScoreDAO getInstance() { // (public static) : 객체 생성 없이도 외부에서 getInstance 메서드 사용.
		if(scoreDAO == null) {
			scoreDAO = new ScoreDAO();
		}
		return scoreDAO;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// DB관련 작업 메서드가 들어가는 공간.
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 커넥션 객체를 제공하는 메서드.
	private static Connection getConnection() throws Exception{
		String url = "jdbc:mysql://localhost:3306/jsp_practice?serverTimezone=Asia/Seoul";
		String uid = "jsp";
		String upw = "jsp";

		return DriverManager.getConnection(url, uid, upw);
	}
	// 각각의 컬럼에 데이터들을 등록하는 메서드.
	@Override
	public boolean insert(ScoreVO score) {

		boolean flag = false;

		String sql = "INSERT INTO scores (name, kor, eng, math, total, average)" + " VALUES(?,?,?,?,?,?)";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, score.getName());
			pstmt.setInt(2, score.getKor());
			pstmt.setInt(3, score.getEng());
			pstmt.setInt(4, score.getMath());
			pstmt.setInt(5, score.getTotal());
			pstmt.setDouble(6, score.getAverage());

			int rn = pstmt.executeUpdate();
			if (rn == 1) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return flag;
	}
	// 전체 목록을 리스트로 가져와서 테이블 형태로 보여주는 메서드.
	@Override
	public List<ScoreVO> list() {
		
		List<ScoreVO> scoreList = new ArrayList<>();

		String sql = "SELECT * FROM scores ORDER BY id ASC";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ScoreVO scores = new ScoreVO( // 객체 생성
						rs.getLong("id"),
						rs.getString("name"),
						rs.getInt("kor"),
						rs.getInt("eng"),
						rs.getInt("math"),
						rs.getInt("total"),
						rs.getDouble("average")
						);
				scoreList.add(scores);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return scoreList;
	}
	// 해당 회원 정보를 삭제하는 메서드.
	@Override
	public boolean delete(long id) {

		boolean flag = false;

		String sql = "DELETE FROM scores WHERE id=?";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, id);
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
	
	// 이름으로 개별 조회하는 메서드.
	@Override
	public List<ScoreVO> search(String keyword) {
		
		List<ScoreVO> scoreList = new ArrayList<>();
		
		String sql = "SELECT * FROM scores" + " WHERE name LIKE ?" + " ORDER BY id ASC";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ScoreVO scores = new ScoreVO( //객체 생성
						rs.getLong("id"),
						rs.getString("name"),
						rs.getInt("kor"),
						rs.getInt("eng"),
						rs.getInt("math"),
						rs.getInt("total"),
						rs.getDouble("average")
						);
				scoreList.add(scores);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return scoreList;
	}
	
	// 아이디로 개별 조회하는 메서드.
	@Override
	public List<ScoreVO> search_Num(long id) {
		
		List<ScoreVO> scoreList = new ArrayList<>();
		
		String sql = "SELECT * FROM scores" + " WHERE id=?" + " ORDER BY id ASC";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ScoreVO scores = new ScoreVO( //객체 생성
						rs.getLong("id"),
						rs.getString("name"),
						rs.getInt("kor"),
						rs.getInt("eng"),
						rs.getInt("math"),
						rs.getInt("total"),
						rs.getDouble("average")
						);
				scoreList.add(scores);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return scoreList;
	}
	
	// 점수를 수정하는 메서드.
	@Override
	public boolean update(ScoreVO score) {
		
		boolean flag = false;
		
		String sql = "UPDATE scores SET kor=?, eng=?, math=?, total=?, average=? WHERE id=?";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, score.getKor());
			pstmt.setInt(2, score.getEng());
			pstmt.setInt(3, score.getMath());
			pstmt.setInt(4, score.getTotal());
			pstmt.setDouble(5, score.getAverage());
			pstmt.setLong(6, score.getId());
			
			int rn = pstmt.executeUpdate();
			if(rn == 1) {
				flag = true;
			}else {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return flag;
	}
}
