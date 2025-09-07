package io.github.choimari.moomoney.util;
/**
 * 콘솔 ANSI 색상 + 텍스트 스타일 Enum
 */
public enum ConsoleStyle {
	 RESET("\u001B[0m"), // 리셋 (모든 스타일 초기화)
	 
	 BOLD("\u001B[1m"),        // 굵게
	 ITALIC("\u001B[3m"),      // 이탤릭 (일부 터미널만 지원)
	 UNDERLINE("\u001B[4m"),   // 밑줄
	 REVERSED("\u001B[7m"),    // 전경색/배경색 반전
	 
	 BLACK("\u001B[30m"),      // 검정
	 RED("\u001B[31m"),        // 빨강
	 GREEN("\u001B[32m"),      // 초록
	 YELLOW("\u001B[33m"),     // 노랑
	 BLUE("\u001B[34m"),       // 파랑
	 PURPLE("\u001B[35m"),     // 보라
	 CYAN("\u001B[36m"),       // 청록
	 WHITE("\u001B[37m"),      // 흰색  
	 
	// ===== 파스텔 & 그레이 계열 (256컬러 기반) =====
	 PASTEL_PINK("\u001B[38;5;218m"),     // 파스텔 핑크
	 PASTEL_MINT("\u001B[38;5;159m"),     // 파스텔 민트
	 PASTEL_YELLOW("\u001B[38;5;229m"),   // 파스텔 노랑
	 PASTEL_BLUE("\u001B[38;5;153m"),     // 파스텔 하늘
	 LIGHT_GREY("\u001B[38;5;250m"),      // 연그레이
	 MEDIUM_GREY("\u001B[38;5;245m"),     // 중간 그레이
	 DARK_GREY("\u001B[38;5;240m"),       // 짙은 그레이
	 
	 BG_BLACK("\u001B[40m"),   // 검정 배경
	 BG_RED("\u001B[41m"),     // 빨강 배경
	 BG_GREEN("\u001B[42m"),   // 초록 배경
	 BG_YELLOW("\u001B[43m"),  // 노랑 배경
	 BG_BLUE("\u001B[44m"),    // 파랑 배경
	 BG_PURPLE("\u001B[45m"),  // 보라 배경
	 BG_CYAN("\u001B[46m"),    // 청록 배경
	 BG_WHITE("\u001B[47m");   // 흰색 배경
	
	private final String code; // ANSI 코드 문자열
	
	private ConsoleStyle(String code) {
		this.code = code;	
		}
	
	public String getCode() {
		return code;
	}
	
	// enum이 문자열로 사용될 때 ANSI 코드 반환
    @Override
    public String toString() {
        return code;
    }
    
    /**
     * ConsoleStyle.RED.apply("메시지")
     * @param message 적용할 문자열
     * @return 단일 스타일 적용된 문자열 (RESET 자동 추가)
     */
    public String apply(String message) {
        return code + message + RESET.code;
    }
    
    /**
     * 다중 스타일 조합 적용 메서드
     * 예: ConsoleStyle.apply("경고!", RED, BOLD, UNDERLINE)
     *
     * @param message 적용할 문자열
     * @param styles 적용할 스타일들 (가변 인자)
     * @return 스타일 적용된 문자열 (RESET 자동 추가)
     */
    public static String apply(String message, ConsoleStyle... styles) {
        StringBuilder sb = new StringBuilder();
        for (ConsoleStyle style : styles) {
            sb.append(style.code);
        }
        sb.append(message).append(RESET.code);
        return sb.toString();
    }

}
