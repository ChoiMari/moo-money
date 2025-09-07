package io.github.choimari.moomoney.factory;

public enum ViewType {
	GUEST("방문객"),
	REGULAR("일반 회원"),
	PREMIUM("프리미엄 회원"),
	ADMIN("관리자"),
	SYSTEM("시스템"),
	MAIN("메인"),
	LOGIN("로그인"),
	SIGNUP("회원가입"),
	RECEIPT_REGISTER("영수증 등록"),   
    RECEIPT_SEARCH("영수증 조회"),   
    RECEIPT_UPDATE("영수증 변경"),     
    RECEIPT_DELETE("영수증 삭제"),     
	REPORT("보고서"),
	MYINFO("내 정보"),
	EXPENSE_SUMMARY("지출 요약"),
	EVENT_LOG("시스템 이벤트 로그");
	
	private String type;
	
	private ViewType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
