package io.github.choimari.moomoney.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


/**
 * 로그 한 건을 표현하는 클래스
 * 	타임스템프, 사용자 email, 이벤트 종류, 처리 결과, 상세 메시지 포함
 */

@AllArgsConstructor @Builder @Getter 
public class LogEntry {
	private final Long id; // 사용자 시퀀스 고유 번호(null 가능 - 비회원)
	private final Role role; // 권한
	private final EventType eventType; // 어떤 이벤트에서 발생 했는지
	private final ResultType resultType; // 결과
	private final String detailMessage; // 실패 사유 등 부가 설명
	@Builder.Default
	private final LocalDateTime timestamp = LocalDateTime.now(); // 로그 발생 시각
	
	// 공용 포맷터 (재사용)
    private static final DateTimeFormatter FORMATTER; 
    
    static {
    	FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    

	
    /**
     * 콘솔/디버깅용
     * 로그 파일에 쓰기 좋은 문자열 포맷으로 변환
     */
    @Override
    public String toString() {
        return String.format("[%s] id=%s, role=%s, event=%s, result=%s, detail=%s",
        	timestamp.format(FORMATTER),
        	(id == null ? roleNameForAnonymous() : id),
            (role == null ? "UNKNOWN" : role.name()),
            (eventType == null ? "UNSPECIFIED_EVENT" : eventType.name()),
            (resultType == null ? "UNSPECIFIED_RESULT" : resultType.name()),
            (detailMessage == null ? "" : detailMessage));
    }
   
    
    /**
     * 파일 저장용
     * 로그 한 건(LogEntry)을 CSV 파일에 저장하기 위해 사용
     * 각 필드를 쉼표로 구분하며, 날짜/시간은 "yyyy-MM-dd HH:mm:ss" 형식으로 포맷팅
     * 파일에 append할 때는 이 문자열 뒤에 개행문자(\n)가 자동 추가되므로 한 줄이 한 로그를 의미
     * 
     * 예시 출력:
     * 2025-09-06 18:00:00,123,USER,SIGNUP,SUCCESS,회원가입 완료
     * @return CSV 형식의 한 줄 문자열
     */
    /*
    public String toCsvLine() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 날짜/시간을 특정 문자열 패턴으로 포맷(년-월-일 시:분:초 형태로 설정)
        return String.format("%s,%s,%s,%s,%s,%s\n",
        	timestamp.format(FORMATTER),
        	(id == null ? roleNameForAnonymous() : id),
            (role == null ? "UNKNOWN" : role.name()),
            (eventType == null ? "UNSPECIFIED_EVENT" : eventType.name()),
            (resultType == null ? "UNSPECIFIED_RESULT" : resultType.name()),
            (detailMessage == null ? "" : detailMessage));
    }
    */
    
    /**
     * OpenCSV용 CSV 배열 반환
     * CSVWriter.writeNext()에 바로 전달 가능
     * 쉼표나 따옴표, 개행 같은 특수문자 처리는 OpenCSV의 CSVWriter가 자동으로 해준다
     * @return LogEntry → String[] 배열로만 넘기면 OpenCSV 입장에서는 String[] 한 줄이 CSV 한 레코드(row).
     */
    public String[] toCsvArray() {
        return new String[] {
            timestamp.format(FORMATTER),
            id == null ? roleNameForAnonymous() : id.toString(),
            role == null ? "UNKNOWN" : role.name(),
            eventType == null ? "UNSPECIFIED_EVENT" : eventType.name(),
            resultType == null ? "UNSPECIFIED_RESULT" : resultType.name(),
            detailMessage == null ? "" : detailMessage
        };
    }
    
    /**
     * 비회원/익명 사용자 표시
     */
    private String roleNameForAnonymous() {
        if (role == Role.GUEST) return "GUEST";
        return "ANONYMOUS";
    }
    
}
