package io.github.choimari.moomoney.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

/**
 * [회원] 모델 클래스
 */
@Builder @AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class User {
	private Long id; // 시퀀스 PK로 사용
	private String email;
	
    @Getter(AccessLevel.NONE) // getter 생성 안함
    @Setter(AccessLevel.NONE) // setter 생성 안함
	private String password; 
 
	private Role role;
	private String name;
	private String nickname;
	private String phone;
	
	/**
	 * 비밀번호 확인 메서드
	 * @param rawPassword : 사용자 입력 평문 패스워드
	 * @return
	 */
//	public boolean matchPw(String rawPassword) {
//		return PasswordUtils.matches(rawPassword, this.password);
//	}
	
//	 // 비밀번호 변경 메서드
//    public void setPasswordRaw(String rawPassword) {
//        this.password = PasswordUtils.hash(rawPassword);
//    }
	
}
