package io.github.choimari.moomoney.model;

public enum Role {
	GUEST("비회원"), // public static final UserType = new UserType("비회원"); 과 같음. 공유 불변 객체 참조변수
	REGULAR_MEMBER("일반 회원"), 
    PREMIUM_MEMBER("프리미엄 회원"), 
	ADMIN("관리자"),
	SYSTEM("시스템 관리자");
	
	private final String label;
	
	private Role(String label){  
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	
}
