package io.github.choimari.moomoney.repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.opencsv.CSVWriter;

import io.github.choimari.moomoney.domain.Category;
import io.github.choimari.moomoney.dto.ReceiptRequest;

/**
 * 영수증 Repository
 * - 파일 기반 저장
 * 영수증 데이터를 월별로 CSV / TXT 파일로 저장
 */
public class ReceiptRepository {
	  private final String basePath = "data/receipts"; // 최상위 폴더

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
    
    /**
     * TXT에서 모든 사용자의 레코드를 읽어 특정 이메일 필터링
     */
    public List<ReceiptRequest> findAllByUser(String userEmail) throws IOException {
        List<ReceiptRequest> result = new ArrayList<>();
        Path base = Paths.get(basePath);
        if (!Files.exists(base)) return result;

        // depth 2: receipts/YYYY-MM/receipts.txt
        try (Stream<Path> stream = Files.walk(base, 2)) {
            stream.filter(path -> path.getFileName().toString().equals("receipts.txt"))
                  .forEach(txtPath -> {
                      try {
                          List<String> lines = Files.readAllLines(txtPath);
                          for (String line : lines) {
                              line = line.trim();
                              if (line.isEmpty()) continue;

                              // TXT 포맷: "이메일: xx | 날짜: yyyy-MM-dd | 카테고리: xx | 금액: xx | 메모: xx"
                              String[] parts = line.split("\\|");
                              if (parts.length < 5) continue;

                              String email = parts[0].split(":")[1].trim();
                              if (!email.equals(userEmail)) continue;

                              LocalDate date = LocalDate.parse(parts[1].split(":")[1].trim());
                              Category category = Category.valueOf(parts[2].split(":")[1].trim());
                              int price = Integer.parseInt(parts[3].split(":")[1].trim());
                              String memo = parts[4].split(":")[1].trim();

                              result.add(new ReceiptRequest(date, category, price, memo));
                          }
                      } catch (IOException e) {
                          e.printStackTrace();
                      }
                  });
        }

        // 최신순 정렬
        result.sort(Comparator.comparing(ReceiptRequest::getDate).reversed());
        return result;
    }

    /**
     * 카테고리별 조회
     */
    public List<ReceiptRequest> findByCategory(String userEmail, Category category) throws IOException {
        return findAllByUser(userEmail).stream()
                .filter(r -> r.getCategory() == category)
                .toList();
    }

    /**
     * 년월별 조회 (yyyy-MM 기준)
     */
    public List<ReceiptRequest> findByMonth(String userEmail, String yearMonth) throws IOException {
        return findAllByUser(userEmail).stream()
                .filter(r -> r.getDate().toString().startsWith(yearMonth)) // 2025-09 등 비교
                .toList();
    }

    /**
     * 년월 + 카테고리 동시 조회
     */
    public List<ReceiptRequest> findByMonthAndCategory(String userEmail, String yearMonth, Category category) throws IOException {
        return findAllByUser(userEmail).stream()
                .filter(r -> r.getDate().toString().startsWith(yearMonth) && r.getCategory() == category)
                .toList();
    }

}
