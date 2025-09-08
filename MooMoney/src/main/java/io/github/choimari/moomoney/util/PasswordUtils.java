package io.github.choimari.moomoney.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 비밀번호 암호화 및 검증 클래스
 *
 * 목적:
 * - 회원가입 시 입력한 평문 비밀번호를 안전하게 저장
 * - 로그인 시 입력한 비밀번호가 저장된 해시와 일치하는지 검증
 *
 * 특징:
 * 1. SHA-256 해시 알고리즘 적용
 *    - SHA-256 : 보안 강력, 256비트 해시 생성
 * 2. 랜덤 Salt 사용
 *    - Rainbow Table 공격 방지
 *    - 같은 비밀번호라도 해시값이 달라짐
 * 3. Base64 인코딩
 *    - 바이트 배열을 안전하게 문자열로 변환
 */
public class PasswordUtils {
	
	private static final int SALT_LENGTH = 32; // salt : 32바이트(256비트)로 설정
	// Rainbow Table 공격 방지, 랜덤 생성 후 해시와 함께 저장됨


    /**
     * 
     * 평문(raw) 비밀번호를 SHA-256 해시 + 랜덤 Salt 적용 후 반환
     * 
     * @param rawPassword : 사용자가 입력한 평문 비밀번호
     * @return String[] : [0] = salt, [1] = hash
     */
	public static String[] hashPassword(String rawPassword) {
        try {
            byte[] saltBytes = new byte[SALT_LENGTH]; //빈 바이트 배열 생성(용도: 랜덤 Salt 저장)
            SecureRandom random = new SecureRandom(); //SecureRandom 객체 생성(보안용 랜덤 숫자 생성, 일반 Random보다 예측 불가)
            random.nextBytes(saltBytes); //saltBytes 배열을 랜덤 값으로 채움
            String salt = Base64.getEncoder().encodeToString(saltBytes); // Salt 바이트 배열을 Base64 문자열로 변환

            MessageDigest md = MessageDigest.getInstance("SHA-256"); //SHA-256 해시 객체 생성
            md.update(saltBytes);// Salt를 해시에 적용 (해시 시작 전에 Salt 섞음)
            byte[] hashedBytes = md.digest(rawPassword.getBytes("UTF-8")); // 평문 비밀번호를 바이트로 변환 후 해싱
            String hash = Base64.getEncoder().encodeToString(hashedBytes); // 해시된 바이트를 Base64 문자열로 변환

            return new String[]{salt, hash}; // [0] = salt, [1] = hash 
        } catch (Exception e) {
            throw new RuntimeException("비밀번호 암호화 실패", e);
        }
    }
	
	/**
     * 로그인 검증용 메서드
     *
     * 단계별 동작:
     * 1. 저장된 Base64 Salt를 바이트 배열로 디코딩
     * 2. SHA-256 해시 객체 생성
     * 3. 디코딩한 Salt를 해시에 적용
     * 4. 사용자가 입력한 평문 비밀번호를 해싱
     * 5. 생성된 해시값과 저장된 해시값 비교
     *
     * @param rawPassword : 사용자가 입력한 평문 비밀번호
     * @param salt        : 저장된 Salt (Base64)
     * @param hash        : 저장된 해시된 비밀번호 (Base64)
     * @return boolean   : 비밀번호 일치(true) / 불일치(false)
     */
    public static boolean verifyPassword(String rawPassword, String salt, String hash) {
        try {
            byte[] saltBytes = Base64.getDecoder().decode(salt); // Base64로 저장된 Salt를 디코딩
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // SHA-256 해시 객체 생성
            md.update(saltBytes); //  Salt를 해시에 적용
            byte[] hashedBytes = md.digest(rawPassword.getBytes("UTF-8")); // 평문 비밀번호를 해싱
            String hashedInput = Base64.getEncoder().encodeToString(hashedBytes); // 해시 바이트를 Base64 문자열로 변환
            return hashedInput.equals(hash); // 기존 저장된 해시와 비교 후 결과 반환
        } catch (Exception e) {
            return false; // 예외 발생 시 false 반환 (검증 실패)
        }
    }

}
