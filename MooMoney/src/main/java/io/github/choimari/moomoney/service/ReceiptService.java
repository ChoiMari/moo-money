package io.github.choimari.moomoney.service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.github.choimari.moomoney.domain.Category;
import io.github.choimari.moomoney.dto.ReceiptRequest;
import io.github.choimari.moomoney.repository.ReceiptRepository;

public class ReceiptService {
	private final ReceiptRepository receiptRepo;
    public ReceiptService(ReceiptRepository receiptRepo) {
        this.receiptRepo = receiptRepo;
    }
    
    /**
     * 영수증 저장
     * @param dto 영수증 DTO
     * @return 저장 성공 여부
     */
    public boolean saveReceipt(ReceiptRequest dto, String email) {
        try {
            // 레포지토리에 실제 저장 처리
            receiptRepo.save(dto, email);
            return true;
        } catch (IOException e) {
            System.out.println("[서버 오류] 영수증 저장 실패. 잠시 후 다시 시도해주세요.");
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 전체 조회 (사용자 이메일 기준)
     * - repo에서 가져온 DTO 리스트를
     *   화면 출력용 Map<Integer, Map<String,String>> 형태로 변환
     */
    public Map<Integer, Map<String, String>> findAllByUser(String userEmail) {
        try {
            List<ReceiptRequest> list = receiptRepo.findAllByUser(userEmail);

            Map<Integer, Map<String, String>> map = new LinkedHashMap<>(); // 순서 보존
            int idx = 1;
            for (ReceiptRequest r : list) {
                Map<String, String> row = new HashMap<>();
                row.put("date", r.getDate().toString());          // yyyy-MM-dd
                row.put("category", r.getCategory().getLabel());  // "식비" 등 라벨
                row.put("price", String.valueOf(r.getPrice()));   // 금액 숫자 → 문자열
                row.put("memo", r.getMemo());
                // row.put("email", userEmail); // 필요시 추가 가능

                map.put(idx++, row); // 1-based index
            }
            return map;

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    /**
     * 카테고리별 조회
     * - 전체 조회 후 category 필터링
     */
    public Map<Integer, Map<String, String>> findByCategory(String userEmail, Category category) {
        Map<Integer, Map<String, String>> all = findAllByUser(userEmail);
        Map<Integer, Map<String, String>> filtered = new LinkedHashMap<>();
        int idx = 1;
        for (Map<String, String> row : all.values()) {
            if (row.get("category").equals(category.getLabel())) {
                filtered.put(idx++, new HashMap<>(row));
            }
        }
        return filtered;
    }

    /**
     * 월별 조회
     * @param yearMonth "2025-09" 형식
     */
    public Map<Integer, Map<String, String>> findByMonth(String userEmail, String yearMonth) {
        Map<Integer, Map<String, String>> all = findAllByUser(userEmail);
        Map<Integer, Map<String, String>> filtered = new LinkedHashMap<>();
        int idx = 1;
        for (Map<String, String> row : all.values()) {
            // "2025-09-06".startsWith("2025-09")
            if (row.get("date").startsWith(yearMonth)) {
                filtered.put(idx++, new HashMap<>(row));
            }
        }
        return filtered;
    }

    /**
     * 월별 + 카테고리별 동시 조회
     * - 특정 월의 특정 카테고리 영수증만 보고 싶을 때
     */
    public Map<Integer, Map<String, String>> findByMonthAndCategory(String userEmail, String yearMonth, Category category) {
        Map<Integer, Map<String, String>> all = findAllByUser(userEmail);
        Map<Integer, Map<String, String>> filtered = new LinkedHashMap<>();
        int idx = 1;
        for (Map<String, String> row : all.values()) {
            if (row.get("date").startsWith(yearMonth)
                    && row.get("category").equals(category.getLabel())) {
                filtered.put(idx++, new HashMap<>(row));
            }
        }
        return filtered;
    }
    
}
