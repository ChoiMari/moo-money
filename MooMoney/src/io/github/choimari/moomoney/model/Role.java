package io.github.choimari.moomoney.model;

public enum Role {
	GUEST("비회원"), // public static final UserType = new UserType("비회원"); 과 같음. 공유 불변 객체 참조변수
	MEMBER("회원"),
	ADMIN("관리자");
	
	private final String label;
	
	Role(String label){  // 기본 private
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	
}
