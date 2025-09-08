package io.github.choimari.moomoney.repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.CSVWriter;

import io.github.choimari.moomoney.dto.ReceiptRequest;

/**
 * 영수증 Repository
 * - 파일 기반 저장
 * 영수증 데이터를 월별로 CSV / TXT 파일로 저장
 */
public class ReceiptRepository {

    /**
     * 영수증 저장
     * @param dto 저장할 영수증 DTO
     * @param userEmail 로그인한 사용자 이메일
     * @throws IOException 파일 쓰기 오류 발생 시
     */
    public void save(ReceiptRequest dto, String userEmail) throws IOException {
        // yyyy-MM 형태로 월별 폴더/파일명 생성
        String yearMonth = dto.getDate().getYear() + "-" + String.format("%02d", dto.getDate().getMonthValue());
        String folderPath = "data/receipts/" + yearMonth;

        // 폴더가 없으면 생성
        Files.createDirectories(Paths.get(folderPath));

        // 각 형식별 파일 경로
        String csvFile = folderPath + "/receipts.csv";
        String txtFile = folderPath + "/receipts.txt";

        // 실제 저장 메서드 호출
        saveCsv(dto, csvFile, userEmail);
        saveTxt(dto, txtFile, userEmail);
    }

    /**
     * CSV 파일로 저장 (OpenCSV 사용)
     * - 파일이 없으면 헤더 추가
     * - 이어쓰기 모드
     */
    private void saveCsv(ReceiptRequest dto, String csvFile, String userEmail) throws IOException {
        boolean fileExists = Files.exists(Paths.get(csvFile)); // 기존 파일 존재 여부 체크

        // CSVWriter 사용 (OpenCSV)
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) { // append = true
            if (!fileExists) {
                // 파일이 없으면 헤더 작성
                writer.writeNext(new String[]{"email", "date", "category", "price", "memo"});
            }

            // DTO 데이터를 문자열 배열로 작성
            writer.writeNext(new String[]{
            	userEmail,                        // 로그인한 사용자 이메일
                dto.getDate().toString(),          // 날짜
                dto.getCategory().name(),          // 카테고리 Enum 이름
                String.valueOf(dto.getPrice()),    // 금액
                dto.getMemo()                      // 메모
            });
        }
    }

    /**
     * TXT 파일로 저장
     * - 사람이 읽기 편한 형식
     */
    private void saveTxt(ReceiptRequest dto, String txtFile, String userEmail) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFile, true))) { // 이어쓰기
            writer.write(String.format("이메일: %s | 날짜: %s | 카테고리: %s | 금액: %d | 메모: %s\n",
            		userEmail,                  // 이메일 추가
                    dto.getDate(),            // 날짜
                    dto.getCategory().name(), // 카테고리 Enum 이름
                    dto.getPrice(),           // 금액
                    dto.getMemo()));          // 메모
        }
    }

}
