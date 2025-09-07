package io.github.choimari.moomoney.util;
/*
[참고 지식] 
	Scanner는 버퍼 사이즈가 1024 char이기 때문에
	많은 입력을 필요로 할 경우에는 성능상 느리다. 
	멀티 쓰레드 환경에서 동기화 X → 안전하지 않음
	
	BufferedReader는 개행문자만 경계로 인식하고 입력받은 데이터가 String으로 고정.
	Scanner보다 속도가 빠르다. 버퍼 사이즈도 8192 char(16,384byte) 이기 때문에 입력이 많을 때 BufferedReader가 유리
	동기화(O) → 멀티 쓰레드 안전 
	
	입력량 많고 성능이 중요하면 → BufferedReader 사용 권장
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 콘솔 입력 전용 싱글톤 클래스
 * BufferedReader를 공용으로 생성하여 View 여러 개에서 공유 가능
 * 프로그램 종료 전까지 계속 사용 가능
 */
public class InputReader {
	private static final InputReader INSTANCE;
	private static final BufferedReader READER;
	
	static { // 즉시 초기화 : 클래스 로딩 시점에 바로 객체 생성(멀티 스레드 안전), InputReader 정도는 메모리에 크게 부담되지 않음
		// 프로그램 시작할 때 거의 반드시 쓰게 되므로
		INSTANCE = new InputReader();
		READER = new BufferedReader(new InputStreamReader(System.in));
	}
	
	private InputReader() {}
	
	/**
	 * 싱글톤 인스턴스 반환 메서드
	 * @return InputReader 참조 객체
	 */
	public static InputReader getInstance() {
//		if(INSTANCE == null) { // 지연 초기화 하지 않음, 이유는 멀티 스레드 환경에서는 동기화 안 하면 두번 생성될 수도 있어서
//			INSTANCE = new InputReader();
//		}
		return INSTANCE;
	}
	
	
	/**
	 * 콘솔에서 한 줄 입력을 받는 메서드
	 *  입력 실패 시 사용자가 올바른 값을 입력할 때까지 반복
	 * 
	 * @param prompt 사용자에게 보여줄 안내 메시지
	 * @return 사용자가 입력한 문자열, 입력 성공 시 반환
	 */
	public String readLine(String prompt) {
		while(true) { // 입력 성공할 때까지 무한 반복
		    try {
			    // 사용자에게 안내 메시지 출력
			    // print()를 사용하여 커서를 같은 줄에 두고 입력받음
				System.out.print(prompt); 
		        // BufferedReader로 한 줄 입력 받기
		        // readLine()은 사용자가 Enter를 누를 때까지 대기하고, 입력된 문자열 반환
		        return READER.readLine(); 
		        
		    } catch (Exception e) {      
		        System.err.println("[입력 오류] 다시 입력해주세요.");
		    }
		}
	}
	
}
