package io.github.choimari.moomoney.service;

import java.util.regex.Pattern;
/**
 * 회원가입 처리 서비스
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
public class SignUpService {
	// 이메일 정규식 
    private final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private final Pattern pattern = Pattern.compile(emailRegex); // 정규식 패턴을 한 번만 컴파일 함
    //pattern.matcher(email).matches()로 검사
    
    // 비밀번호 정규식 : 최소 8자리, 영문(대소문자 가능) + 숫자 + 특수문자(@#$%^&+=!) 1개 이상
    private final String pwRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
    private final Pattern pwPattern = Pattern.compile(pwRegex);
    
    // 닉네임 정규식: 한글/영문/숫자, 2~12자
    private final String nicknameRegex = "^[가-힣a-zA-Z0-9]{2,12}$";
    private final Pattern nicknamePattern = Pattern.compile(nicknameRegex);

	
	/**
	 * 이메일 유효성 검사 메서드
	 * @param Email 사용자 입력값 이메일
	 * @return true 유효, false 사용 불가
	 */
	public boolean validationEmail(String email) {
		if (email == null || email.trim().isEmpty()) return false; //null이거나 공백인 경우
		
		return pattern.matcher(email).matches(); // 패턴 일치 여부
	} 
	
	/**
     * 비밀번호 유효성 검사
     * 최소 8자리, 대소문자/숫자/특수문자 포함
     * @param password 사용자 입력 비밀번호
     * @return true: 유효, false: 사용 불가
     */
    public boolean validationPassword(String password) {
        if (password == null || password.trim().isEmpty()) return false; // null 또는 공백
        return pwPattern.matcher(password).matches(); // 패턴 일치 여부
    }
    
    /**
     * 닉네임 형식 체크
     * 2~12자, 한글/영문/숫자만 허용
     * @param nickname 사용자 입력 닉네임
     * @return true: 유효, false: 사용 불가
     */
    public boolean validationNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) return false; // null 또는 공백
        return nicknamePattern.matcher(nickname).matches(); // 패턴 일치 여부
    }
}
