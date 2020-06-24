package kr.co.jsp.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class MembersManager {

	private static Scanner sc = new Scanner(System.in);
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;

	public static void main(String[] args) {

		while(true) {
			System.out.println("\n### 회원 관리 프로그램 ###");
			System.out.println("# 1. 회원 정보 등록하기");
			System.out.println("# 2. 전체 회원 정보 조회하기");
			System.out.println("# 3. 개인 회원 정보 조회하기");
			System.out.println("# 4. 회원 정보 수정하기");
			System.out.println("# 5. 회원 정보 삭제하기");
			System.out.println("# 6. 프로그램 종료");

			System.out.print("# 메뉴를 입력하세요.(번호) : ");
			int menu = sc.nextInt();

			switch (menu) {
			case 1:
				insert();
				break;
			case 2:
				selectALL();
				break;
			case 3:
				selectOne();
				break;
			case 4:
				update();
				break;
			case 5:
				delete();
				break;
			case 6:
				System.out.println("프로그램을 종료합니다.");
				sc.close();
				System.exit(0);
				break;
			default:
				System.out.println("메뉴를 잘못 입력하셨씁니다.");
				break;
			} // end switch
		} // end while
	}// end main

	// Connection 객체를 제공하는 메서드.
	private static Connection getConnection() throws Exception{

		String driverName = "com.mysql.cj.jdbc.Driver"; // JDBC 커넥터 드라이버 변수 설정.
		String url = "jdbc:mysql://localhost:3306/jsp_practice?serverTimezone=Asia/Seoul"; // DB URL 변수를 설정.
		String uid = "jsp"; // DB사용자 계정명과 암호 변수 설정.
		String upw = "jsp"; // DB사용자 계정명과 암호 변수 설정.
		Class.forName(driverName); // JDBC 커넥터 드라이버 변수로 드라이버 호출.
		// DB와의 연결을 위해 Connection객체 생성. getConnection()으로 호출하여 객체를 생성하고 리턴한다.
		return DriverManager.getConnection(url, uid, upw); 
	}

	// 1. 회원 정보를 INSERT하는 메서드.
	private static void insert() {
		System.out.println("=====================");
		System.out.println("회원 정보를 입력하세요.");
		System.out.print("# 아이디 : ");
		String id = sc.next();

		System.out.print("# 비밀번호 : ");
		String pw = sc.next();

		System.out.print("# 이름 : ");
		String name = sc.next();

		System.out.print("# 이메일 : ");
		String email = sc.next();

		String sql = "INSERT INTO members VALUES(?,?,?,?)";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, email);

			int rn = pstmt.executeUpdate();
			if (rn == 1) {
				System.out.println("회원 정보 저장이 완료되었습니다!");
			} else {
				System.out.println("회원 정보 저장이 실패했습니다!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close(); pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	// 2. 전체 회원 정보를 조회하는 메서드.
	private static void selectALL() {

		String sql = "SELECT * FROM members ORDER BY name ASC";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			System.out.println("=============== 전체 회원 정보 ================");
			while(rs.next()) {
				System.out.println("아이디 : " + rs.getString("id"));
				System.out.println("비밀번호 : " + rs.getString("pw"));
				System.out.println("이름 : " + rs.getString("name"));
				System.out.println("이메일 : " + rs.getString("email"));
				System.out.println("=========================================");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close(); pstmt.close(); conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	// 3. 개인 회원 정보를 조회하는 메서드.
	private static void selectOne() {
		System.out.println("=====================");
		System.out.println("# 조회할 회원의 ID를 입력하세요.");
		System.out.print(">> ");
		String searchId = sc.next();

		String sql = "SELECT * FROM members WHERE id=?";

		try {
			conn = getConnection();

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, searchId);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				System.out.println("============ 회원 정보 ============");
				System.out.println("아이디 : " + rs.getString("id"));
				System.out.println("비밀번호 : " + rs.getString("pw"));
				System.out.println("이름 : " + rs.getString("name"));
				System.out.println("이메일 : " + rs.getString("email"));
				System.out.println("===============================");
			}else {
				System.out.println("해당 회원 정보는 존재하지 않습니다.");				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close(); pstmt.close(); conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	// 4. 개인 회원 정보를 수정하는 메서드.
	private static void update() {
		System.out.println("=====================");
		System.out.println("# 수정할 회원의 ID를 입력하세요.");
		System.out.print(">> ");
		String searchId = sc.next();

		String sql = "SELECT * FROM members WHERE id=?";

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, searchId);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				System.out.printf("# %s(%s)님의 정보를 수정합니다. \n", rs.getString("name"), rs.getString("id"));
				System.out.println("[ 1. 비밀번호 수정 | 2. 이름 수정  | 3. 이메일 수정 | 4. 취소 ]");
				System.out.print("> ");
				int updateNumber = sc.nextInt();
				
				if (updateNumber == 1) {
					System.out.println("# 변경할 회원의 비밀번호를 입력하세요.");
					System.out.print(">> ");
					String updatePw = sc.next();

					String sql2 ="UPDATE members SET pw=? where id=?";

					pstmt = conn.prepareStatement(sql2);

					pstmt.setString(1, updatePw);
					pstmt.setString(2, rs.getString("id"));

					int rn = pstmt.executeUpdate();
					if (rn == 1) {
						System.out.println("비밀번호 변경이 완료되었습니다!");
					} else {
						System.out.println("비밀번호 변경이 실패하였습니다!");
					}
					
				} else if (updateNumber == 2) {
					System.out.println("# 변경할 회원의 이름을 입력하세요.");
					System.out.print(">> ");
					String updateName = sc.next();

					String sql3 ="UPDATE members SET name=? where id=?";

					pstmt = conn.prepareStatement(sql3);

					pstmt.setString(1, updateName);
					pstmt.setString(2, rs.getString("id"));

					int rn = pstmt.executeUpdate();
					if (rn == 1) {
						System.out.println("이름 변경이 완료되었습니다!");
					} else {
						System.out.println("이름 변경이 실패하였습니다!");
					}

				} else if (updateNumber == 3) {
					System.out.println("# 변경할 회원의 이메일을 입력하세요.");
					System.out.print(">> ");
					String updateEmail = sc.next();

					String sql4 ="UPDATE members SET email=? where id=?";

					pstmt = conn.prepareStatement(sql4);

					pstmt.setString(1, updateEmail);
					pstmt.setString(2, rs.getString("id"));

					int rn = pstmt.executeUpdate();
					if (rn == 1) {
						System.out.println("이메일 변경 성공!");
					} else {
						System.out.println("이메일 변경 실패!");
					}
				} else if (updateNumber == 4) {
					System.out.println("변경을 취소하셨습니다.");
				}
			}else {
				System.out.println("해당 회원 정보는 존재하지 않습니다.");				
			}  
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close(); conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	// 5. 회원 정보를 삭제하는 메서드.
	private static void delete() {
		System.out.println("=====================");
		System.out.println("삭제할 회원의 ID를 입력하세요.");
		System.out.print("# 아이디 : ");
		String deleteId = sc.next();
		
		String sql = "DELETE FROM members WHERE id=?";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deleteId);
			int rn = pstmt.executeUpdate();
			if (rn == 1) {
				System.out.println("회원 ID : " + deleteId + "이(가) 정상적으로 삭제되었습니다.");
			} else {
				System.out.println("해당 회원 정보가 삭제되지 않았습니다.");
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close(); conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
	}
}// end class
