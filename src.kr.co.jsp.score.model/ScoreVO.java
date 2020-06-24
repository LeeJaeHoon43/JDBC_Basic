package kr.co.jsp.score.model;
/*
# 데이터베이스 새로운 테이블 scores 생성.
 CREATE TABLE scores (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    kor INT(10) DEFAULT 0,
    eng INT(10) DEFAULT 0,
    math INT(10) DEFAULT 0,
    total INT(20) NOT NULL,
    average FLOAT(5, 2) # 최대 자리수 (소수점 포함 5자리까지), 소수점 이하 2자리
); 
*/

public class ScoreVO {
	
	private long id; // DB에서 BIGINT자료형은 자바에서 long형이다.
	private String name; // DB에서 BARCHAR자료형은 자바에서 String형이다.
	private int kor;
	private int eng;
	private int math;
	private int total;
	private double average; // DB에서 FLOAT자료형은 자바에서 double형이다.
	
	public ScoreVO() {} 

	public ScoreVO(long id, String name, int kor, int eng, int math, int total, double average) {
		super();
		this.id = id;
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.total = total;
		this.average = average;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public int getTotal() {
		return total;
	}
	
	public void setTotal() {
		this.total = this.kor + this.eng + this.math;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage() {
		this.average = this.total / 3.0;
	}
}
