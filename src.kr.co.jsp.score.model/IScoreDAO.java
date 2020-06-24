package kr.co.jsp.score.model;

import java.util.List;
/*
# 인터페이스
- 모든 메서드가 추상 메서드로만 이루어져 있다.
- 클래스 혹은 프로그램이 제공하는 기능을 명시적으로 선언하는 역할.
- 추상 메서드와 상수로만 이루어져 있다.
- 구현된 코드가 없기 때문에 당연히 인터페이스로 인스턴스를 생성할 수 없다. 
- 클라이언트 프로그램에 어떤 메서드를 제공하는지 미리 알려주는 명세서 또는 약속의 역할을 한다.
- 인터페이스를 구현한 클래스가 어떤 기능의 메서드를 제공하는지 명세하는 것이다.
*/

public interface IScoreDAO {
	
	// 점수를 등록하는 기능.
	boolean insert(ScoreVO score);
	
	// 점수 목록을 조회.
	List<ScoreVO> list();
	
	// 이름으로 검색하는 기능.
	List<ScoreVO> search(String keyword);
	
	// 아이디로 검색하는 기능.
	List<ScoreVO> search_Num(long id);
	
	// 점수를 수정하는 기능.
	boolean update(ScoreVO score);
	
	// 삭제 기능.
	boolean delete(long id);
}
