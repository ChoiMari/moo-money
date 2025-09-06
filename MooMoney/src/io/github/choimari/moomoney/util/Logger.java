package io.github.choimari.moomoney.util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import io.github.choimari.moomoney.model.EventType;
import io.github.choimari.moomoney.model.LogEntry;
import io.github.choimari.moomoney.model.ResultType;
import io.github.choimari.moomoney.model.Role;
/**
 * 로그/추적 기능 클래스 - 모든 서비스에서 공용(static 자원) 사용 가능
 * 싱글톤으로 관리, 로그 기록 및 읽기 지원
 * 시스템 관리자만 로그 읽기 가능
 * 
 * 이벤트 : 회원 가입, 수정, 삭제, 로그인 시도를 로그 파일에 기록함
 * 
 * 로그 관리 내용 :
 * 	타임스탬프
 * 	사용자 이메일
 * 	이벤트 종류
 * 	처리 결과(성공/실패)
 * 
 * 파일 : data/logs.csv에 저장하여 관리함
 * 보안 : 읽기 전용 권한 설정(실제 파일 권한은 운영체제에서 설정함)
 * 
 * 실패 시 예외처리 로깅
 * 로그 파일이 없으면 자동 생성
 */
public class Logger {
	private static final Logger INSTANCE;
	private static final String LOG_FILE;
	private final File logFile;
	static { 
		INSTANCE = new Logger(); // 즉시 초기화
		LOG_FILE = "data/logs.csv"; //로그 파일 저장 경로(상대 경로 : 프로젝트 루트 기준)
		//로그파일 -> 읽기 전용 권한 설정은 운영체제에서 처리
	}
	
	private Logger() { // 생성자 외부 호출 막음
		logFile = new File(LOG_FILE);
        if(!logFile.exists()) { // 실제로 존재하는지 확인
        	logFile.getParentFile().mkdirs(); 
        	// getParentFile() : 경로의 상위 디렉토리를 반환
        	// mkdirs() : 상위 디렉토리(들)가 존재하지 않으면 모두 생성
        	 try {
        		 logFile.createNewFile(); // 파일 생성
             } catch (IOException e) {
            	 System.err.println("로그 파일 생성 실패: " + e.getMessage());
                 e.printStackTrace();
             }
        }
	} 
	
	public static Logger getInstance() {
		return INSTANCE;
	}
	
	/**
	 * (내부 전용)LogEntry 생성 메서드
	 * 권한 별로 ID나 상세 메시지를 처리하여 LogEntry를 반환
	 * @param id 사용자 고유 시퀀스 번호(비회원은 null로 처리)
	 * @param role 권한
	 * @param eventType 이벤트 타입
	 * @param resultType 처리 결과
	 * @param detail 상세 메시지(null 가능)
	 * @return 생성된 LogEntry 객체
	 */
	private LogEntry createEvent(Long id, Role role, EventType eventType, ResultType resultType, String detail) {
		detail = (detail == null ? "" : detail);  // 상세 메시지 처리: null -> 빈 문자열
	   
		// 비회원 처리
	    if (role == Role.GUEST) {
	        id = null;
	    }
	    
	    // 관리자 처리: detail에 *로 강조 표시
	    if (role == Role.ADMIN || role == Role.SYSTEM) {
	    	detail = "*" + detail + "*";
	    }

	    return LogEntry.builder()
	            .id(id)
	            .role(role)
	            .eventType(eventType)
	            .resultType(resultType)
	            .detailMessage(detail)
	            .build();
	}
	
	/**
	 * 로그 기록 메서드
	 * CSV 형식으로 로그 파일에 append
	 * 단일/멀티 쓰레드 환경에서 안전하게 동기화 처리
	 * @param id 사용자 고유 번호(시퀀스), (null 가능 - 비회원)
	 * @param role 사용자 권한
	 * @param eventType 이벤트 종류(회원가입, 로그인 등)
	 * @param result 처리 결과
	 * @param detailMessage 추가 설명 (null 가능)
	 */
	public synchronized void log(Long id, Role role, EventType eventType, ResultType result, String detail) {
		//LogEntry 객체 생성
		LogEntry entry = createEvent(id, role, eventType, result, detail);
		
		//CSV 기록
        try (CSVWriter writer = new CSVWriter(new FileWriter(logFile, true))) {
            writer.writeNext(entry.toCsvArray()); // toCsvArray: String[]로 변환
        } catch (IOException ex) {
            System.err.println("로그 기록 실패: " + ex.getMessage());
            ex.printStackTrace();
        }
	}
	
	 /**
     * 로그 읽기 (SYSTEM 관리자만 가능)
     * 통으로 읽기: 파일 전체를 한 번에 읽어 처리
     * @param requesterRole 요청자 권한
     * @return 로그 리스트 (권한 없으면 빈 리스트)
     */
	 public List<LogEntry> readAll(Role requesterRole) {
	        if (requesterRole != Role.SYSTEM) {
	            System.err.println("로그 읽기 권한 없음: SYSTEM 관리자만 접근 가능");
	            return new ArrayList<>();
	        }

	        List<LogEntry> entries = new ArrayList<>();
	        if (!logFile.exists()) return entries;

	        try (CSVReader reader = new CSVReader(new FileReader(logFile))) {
	            List<String[]> lines = reader.readAll(); // 통으로 읽기
	            for (String[] tokens : lines) {
	                if (tokens.length < 6) continue;

	                Long id = "ANONYMOUS".equals(tokens[1]) || "GUEST".equals(tokens[1]) ? null : Long.parseLong(tokens[1]);
	                Role role = Role.valueOf(tokens[2]);
	                EventType event = EventType.valueOf(tokens[3]);
	                ResultType result = ResultType.valueOf(tokens[4]);
	                String detail = tokens[5];

	                entries.add(LogEntry.builder()
	                        .id(id)
	                        .role(role)
	                        .eventType(event)
	                        .resultType(result)
	                        .detailMessage(detail)
	                        .build());
	            }
	        } catch (IOException ex) {
	            System.err.println("로그 읽기 실패: " + ex.getMessage());
	            ex.printStackTrace();
	        } catch (CsvException e) {
	            e.printStackTrace(); // CSV 파싱 오류 처리
	        }

	        return entries;
	    }
}
