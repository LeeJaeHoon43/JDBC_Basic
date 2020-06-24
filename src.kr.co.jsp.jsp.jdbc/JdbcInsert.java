package kr.co.jsp.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcInsert {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("# 아이디  : ");
		String id = sc.next();
		
		System.out.print("# 비밀번호 : ");
		String pw = sc.next();
		
		System.out.print("# 이름  : ");
		String name = sc.next();
		
		System.out.print("# 이메일  : ");
		String email = sc.next();
		
		// DB 연동 순서.
		
		// 1. DB사용자 계정명과 암호, DB URL 등의 초기 데이터 변수를 설정.
		String url = "jdbc:mysql://localhost:3306/jsp_practice?serverTimezone=Asia/Seoul";
		String uid = "jsp"; // root
		String upw = "jsp"; // mysql
		
		// try 안에서 사용할 수 있도록 미리 객체를 생성해 초기화한다.
		Connection conn = null;
		Statement stmt = null;
		
		try {
			// 2. JDBC 커넥터 드라이버 호출.
			Class.forName("com.mysql.cj.jdbc.Driver"); // 고정값이다.
			
			// 3. DB연동을 위한 클래스들의 객체를 생성.
			/*
			(1) Connection 객체.
			 - DB와의 연결을 위한 객체. Connection객체를 생성하려면 직접 new 연산자를 통해 객체를 생성할 수 없다.
			 - DriverManager클래스가 제공하는 정적 메서드인 getConnection()을 호출하여 객체를 생성한다.
			 */
			conn = DriverManager.getConnection(url, uid, upw);
			
			/*
			 (2) Statement 객체.
			 - SQL문을 실행하기 위한 객체.
			 - Statement객체는 Connection객체가 제공하는 createStatement()메서드를 호출하여 실행한다.
			 */
			stmt = conn.createStatement();
			
			/*
			 # SQL문 작성.
			 - SQL문은 String 형태로 작성.
			 */
			String sql = "INSERT INTO members VALUES " + "('" + id + "', '" + pw + "', '" + name + "', '" + email + "')";
			
			/*
			 # Statement 객체를 통한 SQL문 실행.
			 1. INSERT, UPDATE, DELETE 명령일 경우, executeUpdate()를 호출한다.
			 2. SELECT 명령일 경우, executeQuery()를 호출한다.
			 3. executeUpdate()메서드는 실행 성공 시 성공한 쿼리문의 갯수를, 실패 시 0을 리턴한다.
			 */
			int resultNum = stmt.executeUpdate(sql); // spl문을 실행하고 성공한 갯수를 변수에 저장한다.
			if(resultNum == 1) { // 갯수가 1일 경우, 아래의 문장 출력.
				System.out.println("DB에 회원정보 저장 성공!");
			} else { // 갯수가 1이 아닐 경우, 아래의 문장 출력.
				System.out.println("DB에 회원정보 저장 실패!");
			}
		} catch (Exception e) {
			e.printStackTrace(); // 에러가 발생한 메서드의 호출 기록을 출력해주는 메서드.
		} finally {
			/*
			 4. DB연동 객체들 자원을 반납. 
			 - 원활한 JDBC 프로그래밍을 위해 사용한 자원을 반납한다.
			 */
			try {
				conn.close(); stmt.close(); sc.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
