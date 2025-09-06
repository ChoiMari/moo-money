package io.github.choimari.moomoney.util;
/**
 * 비밀번호 해시 처리 유틸
 * 	SHA-256 + 랜덤 Salt 적용
 *  .csv 파일에 암호화로 저장해서 관리
 */
public class PasswordUtils {
	private static final String FILE_PATH = ""; 
	private static final int SALT_LENGTH = 32; // salt : 32바이트(256비트)로 설정
	// Rainbow Table 공격 방지, 랜덤 생성 후 해시와 함께 저장됨

}
