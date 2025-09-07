package io.github.choimari.moomoney.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import io.github.choimari.moomoney.domain.User;

/**
 * 파일 기반으로 회원 데이터를 조회, 저장하는 클래스
 * 이메일, 닉네임 중복검사, 회원 정보 저장
 */
public class UserRepository {
	private final String path = "data/users.txt"; // 저장할 경로 설정
	
	/**
	 * 이메일 중복 검사
	 */
//	public boolean existsByEmail(String email) throws IOException{
//		try(BufferedReader br = new BufferedReader(new FileReader(path))) { // 자원 자동으로 닫아줌
//			// 문자 단위로 읽는 기본 스트림에 성능 향상을 위해 버퍼 보조스트림을 꽂음
//			// 내부 버퍼 8kb 크기 바이트 배열에 모았다가 씀(I/O 호출 횟수 감소)
//			String line = "";
//			while((line = br.readLine()) != null) { // 한 줄씩 읽고 담아서 비교
//				// 더이상 읽을 데이터가 없으면 null 반환(반복문 종료)
//				if() {
//					
//				}
//			}
//		} catch() {
//			
//		}
//	}
	
	// 3. 회원 저장
    public void save(User user) throws IOException {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path, true)))) { 
        	// 기존 데이터에 이어씀
            pw.printf("%s,%s,%s,%s\n",
            			
                      user.getEmail(),
                      user.getPassword(),
                      user.getRole().name(),
                      user.getNickname());
        }
    }
	
}
