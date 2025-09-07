package io.github.choimari.moomoney.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import io.github.choimari.moomoney.domain.User;

/**
 * 파일 기반으로 회원 데이터를 조회, 저장하는 클래스
 * 이메일, 닉네임 중복검사, 회원 정보 저장
 */
public class TXTUserRepository implements IUserRepository{
	private final String path = "data/users.txt"; // 저장할 경로 설정
	private File file;
	public TXTUserRepository() {
		file = new File(path);
		if (!file.exists()) { // 실제로 파일이 존재하지 않으면
		    try {
		    	file.getParentFile().mkdirs(); // 상위 폴더 없으면 생성
				file.createNewFile(); // 파일 생성
			} catch (IOException e) {
				System.out.println("[ERROR] txt 파일 생성 실패: " + e.getMessage());
				e.printStackTrace();
			}        
		}
	}
	
    /**
     *  이메일 중복 체크
     * @param email 사용자 입력 이메일
     * @return 중복 체크 결과(true : 중복이므로 사용불가, false 중복없으므로 사용가능) 
     * @throws IOException 파일 입출력 작업으로 예외 처리 필요
     */
    public boolean existsByEmail(String email) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = "";
            while((line = br.readLine()) != null) { // 한줄씩 읽어서 line에 담아서 null체크
            	// 더이상 읽을 수 없으면 null반환하므로 반복 종료됨
                String[] tokens = line.split(","); // ,를 기준으로 자름
                if(tokens[0].equals(email)) return true; // email 위치 : 같은게 있음(중복) true리턴
            }
        }
        return false;
    }

    /**
     * 닉네임 중복 체크
     * @param nickname 사용자 입력 닉네임
     * @return 중복체크 결과
     * @throws IOException 파일 입출력 작업으로 예외 처리 필요
     */
    public boolean existsByNickname(String nickname) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = "";
            while((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if(tokens[4].equals(nickname)) return true; // nickname 위치(tokens[4])
            }
        }
        return false;
    }
	
	/**
	 * 회원 저장
	 * @param user (dto타입에서 변환된) User객체
	 * @throws IOException 파일입출력 작업을 하기때문에 예외처리 필요
	 */
    @Override
    public void save(User user) throws IOException {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path, true)))) { 
        	//new FileWriter(path, true) -> true : 기존 데이터에 이어씀
            pw.printf("%s,%s,%s,%s,%s\n", // 이 저장 순서 중요함!!(이 위치로 중복체크 하기 때문에)
                      user.getEmail(), // [0]
                      user.getPasswordSalt(), //[1]
                      user.getPasswordHash(), //[2]
                      user.getRole().name(), //[3]
                      user.getNickname());//[4]
        }
    }
	
}
