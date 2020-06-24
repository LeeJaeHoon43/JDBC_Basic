package kr.co.jsp.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JdbcSelect2 {
	public static void main(String[] args) {
		// 회원의 ID를 입력받아 해당 ID의 회원정보를 모두 출력하는 JDBC 프로그래밍 코드를 작성하시오. (개별 조회)
		Scanner sc = new Scanner(System.in);
		System.out.println("# 조회할 회원의 ID를 입력하세요.");
		System.out.print(">> ");
		String searchId = sc.next();
		
		String sql = "SELECT * FROM members WHERE id=?";
		
		String url = "jdbc:mysql://localhost:3306/jsp_practice?serverTimezone=Asia/Seoul";
		String uid = "jsp";
		String upw = "jsp";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, searchId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
					System.out.println("============ 회원 정보 ==========");
					System.out.println("아이디 : " + rs.getString("id"));
					System.out.println("비밀번호 : " + rs.getString("pw"));
					System.out.println("이름 : " + rs.getString("name"));
					System.out.println("이메일 : " + rs.getString("email"));
					System.out.println("=============================");
			}else {
				System.out.println("해당 회원 정보는 존재하지 않습니다.");				
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
