package io.github.choimari.moomoney.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.github.choimari.moomoney.util.PasswordUtils;
import lombok.AccessLevel;

/**
 * [회원] 모델 클래스
 */
@Builder @AllArgsConstructor @Getter 
@EqualsAndHashCode 
public class User {
	//private Long id; // 시퀀스 PK로 사용
	private String email;
    private String passwordHash; // 비밀번호 해시
    private String passwordSalt; // 비밀번호 Salt
 
	private Role role;
	private String nickname;
	
//	/**
//     * 비밀번호 설정 메서드
//     * - 평문 비밀번호를 받아서 Salt + Hash 생성 후 저장
//     * @param rawPassword : 사용자가 입력한 평문 비밀번호
//     */
//    public void setPasswordRaw(String rawPassword) {
//        String[] saltAndHash = PasswordUtils.hashPassword(rawPassword);
//        this.passwordSalt = saltAndHash[0]; // [0] = salt
//        this.passwordHash = saltAndHash[1]; // [1] = hash
//    }
	
//	/**
//     * 비밀번호 설정 메서드
//     * @param rawPassword : 사용자가 입력한 평문 비밀번호
//     * @return [0] salt [1] hash
//     */
//    public String[] setPasswordRaw(String rawPassword) {
//        return PasswordUtils.hashPassword(rawPassword);
//    }

    /**
     * 비밀번호 확인 메서드
     * - 로그인 시 입력 비밀번호와 저장된 해시 검증
     * @param rawPassword : 입력된 평문 비밀번호
     * @return true : 일치, false : 불일치
     */
    public boolean matchPw(String rawPassword) {
        if(this.passwordSalt == null || this.passwordHash == null) {
            return false; // 비밀번호가 설정되지 않은 경우
        }
        return PasswordUtils.verifyPassword(rawPassword, this.passwordSalt, this.passwordHash);
    }
	
    /**
     *  등급 업그레이드
     *  일반회원(REGULAR_MEMBER) → 프리미엄회원(PREMIUM_MEMBER)
     */
    public void upgradeToPremium() {
        if(role == Role.REGULAR_MEMBER) {
            role = Role.PREMIUM_MEMBER;
        }
    }
	
}
