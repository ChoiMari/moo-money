package io.github.choimari.moomoney.domain;

public enum EventType {
	SIGNUP("회원가입"),
	SIGNIN("로그인 시도"),
	SIGNOUT("로그아웃"),
	ADD_RECEIPT("영수증 추가"),
	UPDATE_USER("회원 정보 수정"),
	DELETE_USER("회원 탈퇴");
	
	private final String type;
	
	private EventType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
