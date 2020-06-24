package kr.co.jsp.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcSelect {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/jsp_practice?serverTimezone=Asia/Seoul";
		String uid = "jsp";
		String upw = "jsp";
		
		String sql = "SELECT * FROM members"; // 전체 조회의 경우.
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // select문에서만 사용하는 객체.
		
		try {
			/*
			   - Connection 객체명 = DriverManager.getConnection("데이터베이스주소","사용자","암호")
	  		   - 연동객체를 생성한다. 데이터베이스와 연결객체를 담고 있다.
	  		   - DriverManager.getConnetion( )은 단독으로 사용하지 못하고, Class.forName( )이 있어야 사용할 수 있다.
	  		   - PreparedStatement 객체명  = Connection객체.prepareStatement(쿼리문)
	 		   - 미리 컴파일 하기 때문에 실행속도가 빠르다. 잦은 반복 실행되는 문장에 사용한다.
	 		   - Statement객체는 실행시 SQL문장을 입력하고,PreparedStatement객체는 생성시 SQL문장을 입력한다. 
    		   - 만약, Statement객체를 생성시 SQL문자를 넣거나 PreparedStatement객체 실행시 SQL문장을 입력하면 에러가 발생한다.
			 */
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			
			/*
			 * ResultSet의 객체를 pstmt의 executeQuery()를 통해 받아옵니다.
			 * ResultSet 객체명 = stmt.excuteQurey(SELECT 쿼리문).
 			 - Statement객체를 이용하여 생성, select문을 실행한다.
  			 - 메서드로는 next( ), getString( ), getInt( ) 등이 있다.
  			 - ResultSet 객체가 제공하는 next() 메서드는 Select 쿼리문의 결과값의 존재 여부를 확인하는 메서드이다.
			 */
			rs = pstmt.executeQuery(); // 쿼리를 실행한다.(SELECT 쿼리만)
			while(rs.next()) {
				// SELECT의 실행 결과의 컬럼값을 읽어오려면 rs의 getString(), getInt()등의 메서드를 사용한다.
				// 해당 메서드의 매개값으로 읽어올 테이블의 컬럼명을 지정한다.
				// SELECT 쿼리문의 실행 결과, 조회할 데이터가 한 줄이라도 존재한다면 rs객체의 next()메서드는 true를 리턴하고 해당 행을 지목한다.
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				String email = rs.getString("email");
				System.out.printf("# 아이디 : %s\n# 비밀번호 : %s\n# 이름 : %s\n# 이메일 : %s\n", id, pw, name, email);
				System.out.println("-------------------------------------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close(); pstmt.close(); rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}