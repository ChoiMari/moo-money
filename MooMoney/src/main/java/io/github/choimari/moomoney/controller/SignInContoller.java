package io.github.choimari.moomoney.controller;

import java.util.regex.Pattern;

/**
 * 회원가입 처리 컨트롤러
 * 유효성 체크
 * 	- 이메일 검증
 *  - 비밀번호 검증
 *  - 닉네임 검증
 * 
 * 중복 검사
 *	- 이메일
 *  - 닉네임
 *    
 * User 생성
 */
public class SignInContoller {
    private final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private final Pattern pattern = Pattern.compile(emailRegex); // 정규식 패턴을 한 번만 컴파일 함
    //pattern.matcher(email).matches()로 검사
	
	/**
	 * 이메일 유효성 검사 메서드
	 * @param Email 사용자 입력값 이메일
	 * @return true 유효, false 사용 불가
	 */
	public boolean validationEmail(String email) {
		if (email == null || email.trim().isEmpty()) return false; //null이거나 공백인 경우
		
		if(pattern.matcher(email).matches()) return true;
		// 이메일이 패턴과 정확히 일치하는지 확인
		
		return false;
	} 
	
	
}
