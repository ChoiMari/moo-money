package io.github.choimari.moomoney.domain;

/**
 * 로그 / 추적용 처리 결과
 */
public enum ResultType {
	SUCCESS("성공"),
	FAIL("실패");
	
	private final String message;
	private ResultType(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
