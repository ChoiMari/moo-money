package io.github.choimari.moomoney.repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

import io.github.choimari.moomoney.domain.User;
import io.github.choimari.moomoney.dto.SignUpRequest;

/**
 * 회원 가입한 사용자의 정보를 파일 기반(.csv)으로 저장
 * 	IUserRepository 구현체
 * 	OpenCSV 사용(외부 라이브러리)
 */
public class CSVUserRepository implements IUserRepository{
	private final String file_path = "data/users.csv"; // 저장 시킬 CSV 파일 경로
	private File file;
	
	public CSVUserRepository(){
		file = new File(file_path);
		try {
			if(!file.exists()) { // 파일이 없으면
			file.getParentFile().mkdirs(); //상위 폴더들을 생성
			file.createNewFile(); // 파일 생성
			}
		} catch (IOException e) {
			System.out.println("[ERROR] CSV 파일 생성 실패: " + e.getMessage());
			e.printStackTrace();
		} 
	}

	/**
	 * 가입한 회원의 정보 저장
	 * csv에 한 줄 씩 저장
	 * append로 기존 데이터 유지
	 * @param user : 저장할 User 객체
	 */
	@Override
	public void save(User user) {
		try(CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
			// FileWriter는 파일에 문자 데이터를 쓰는 자바 클래스, file은 write할 파일 객체, true는 append모드(기존 파일 내용 뒤에 추가한다)
			// CSVWriter writer = new CSVWriter(...)
			// CSVWriter는 OpenCSV에서 제공하는 CSV 전용 출력 클래스, CSV 형식 처리 자동으로 해줌 (쉼표, 따옴표, 개행 등)
			// CSVWriter를 사용하면 writer.writeNext(String[] line) 같은 간단한 메서드로 한 줄씩 CSV 형식으로 안전하게 저장 가능
			 // CSV 한 줄 데이터
//            String[] line = new String[] {
//                user.getId().toString(),
//                user.getEmail(),
//                user.getPasswordHash(), // 안전하게 Hash 저장
//                user.getPasswordSalt(), // Salt 저장
//                user.getRole().name(),
//                user.getName(),
//                user.getNickname(),
//                user.getPhone()
//            };
//            writer.writeNext(line); // CSV에 추가
			
		}catch(IOException ex) {
			
		}
		
	}


}
