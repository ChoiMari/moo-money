package io.github.choimari.moomoney.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import io.github.choimari.moomoney.domain.Category;
import io.github.choimari.moomoney.dto.ReceiptRequest;
import io.github.choimari.moomoney.repository.ReceiptRepository;
import io.github.choimari.moomoney.util.ConsoleStyle;

public class ReportService {

    private final ReceiptRepository receiptRepo;

    public ReportService(ReceiptRepository receiptRepo) {
        this.receiptRepo = receiptRepo;
    }

    /**
     * 월별 보고서 생성 + 출력 + 파일 저장
     */
    public void showMonthlyReport(String email, String yearMonthStr) throws IOException {
        List<ReceiptRequest> receipts = receiptRepo.findAllByUser(email);

        // 입력받은 연월 기준 필터링
        YearMonth targetMonth = YearMonth.parse(yearMonthStr, DateTimeFormatter.ofPattern("yyyy-MM"));
        List<ReceiptRequest> monthlyReceipts = receipts.stream()
                .filter(r -> YearMonth.from(r.getDate()).equals(targetMonth))
                .sorted(Comparator.comparing(ReceiptRequest::getDate))
                .collect(Collectors.toList());

        if (monthlyReceipts.isEmpty()) {
            System.out.println("[알림] 선택한 연월의 지출 내역이 없습니다.");
            return;
        }

        // 카테고리별 합계 계산
        Map<Category, Integer> categorySum = monthlyReceipts.stream()
                .collect(Collectors.groupingBy(ReceiptRequest::getCategory,
                        TreeMap::new,
                        Collectors.summingInt(ReceiptRequest::getPrice)));

        int totalSum = monthlyReceipts.stream().mapToInt(ReceiptRequest::getPrice).sum();

        // 보고서 문자열 생성
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("-------------------------------------------------------\n"));
        sb.append(String.format("%-15s %s 지출 보고서 \n", "", yearMonthStr));
        sb.append(String.format("-------------------------------------------------------\n"));
        sb.append(String.format("%-12s %-15s %-10s\n", "날짜", "금액", "카테고리"));
        sb.append("-------------------------------------------------------\n");

        for (ReceiptRequest r : monthlyReceipts) {
            sb.append(String.format("%-15s %-15s %-10s\n",
                    r.getDate(),
                    String.format("%,d원", r.getPrice()),
                    r.getCategory().getLabel()));
            sb.append(String.format("\n메모 : %s\n\n", r.getMemo()));
        }

        sb.append("-------------------------------------------------------\n");
        sb.append("카테고리별 합계:\n\n");
        for (var entry : categorySum.entrySet()) {
            sb.append(String.format("%-8s %s원\n", entry.getKey().getLabel(), String.format("%,d", entry.getValue())));
        }
        sb.append("-------------------------------------------------------\n");
        sb.append(String.format("총 합계: %,d원\n", totalSum));
        sb.append(String.format("\n%-10s요청 계정 : %s\n\n", "", email));
        sb.append(String.format("%-15s-한눈에 지출-\n", "", email));
        // 콘솔 출력
        System.out.println(ConsoleStyle.apply("\n" + sb.toString(), ConsoleStyle.GREEN));

        // 자동 저장
        String filename = String.format("%s-%s-월별보고서.txt", email, yearMonthStr);
        saveReportToFile(filename, sb.toString());
    }

    /**
     * 카테고리별 보고서 생성 + 출력 + 파일 저장
     */
    public void showCategoryReport(String email, String yearMonthStr) throws IOException {
        List<ReceiptRequest> receipts = receiptRepo.findAllByUser(email);

        YearMonth targetMonth = YearMonth.parse(yearMonthStr, DateTimeFormatter.ofPattern("yyyy-MM"));
        List<ReceiptRequest> monthlyReceipts = receipts.stream()
                .filter(r -> YearMonth.from(r.getDate()).equals(targetMonth))
                .sorted(Comparator.comparing(ReceiptRequest::getCategory)
                        .thenComparing(ReceiptRequest::getDate))
                .collect(Collectors.toList());

        if (monthlyReceipts.isEmpty()) {
            System.out.println("[알림] 선택한 연월의 지출 내역이 없습니다.");
            return;
        }

        // 카테고리별 그룹화
        Map<Category, List<ReceiptRequest>> categoryMap = monthlyReceipts.stream()
                .collect(Collectors.groupingBy(ReceiptRequest::getCategory, TreeMap::new, Collectors.toList()));

        int totalSum = monthlyReceipts.stream().mapToInt(ReceiptRequest::getPrice).sum();

        StringBuilder sb = new StringBuilder();
        sb.append("===========================================================\n");
        sb.append(String.format("    %s 카테고리별 지출 보고서 (%s) \n", yearMonthStr, email));
        sb.append("===========================================================\n\n");

        for (var entry : categoryMap.entrySet()) {
            Category category = entry.getKey();
            List<ReceiptRequest> list = entry.getValue();
            int sum = list.stream().mapToInt(ReceiptRequest::getPrice).sum();

            sb.append(String.format("[카테고리: %s]\n", category.getLabel()));
            sb.append("-------------------------------------------------------\n");
            sb.append(String.format("%-12s | %-14s | %-20s\n", "날짜", "금액", "메모"));
            sb.append("-------------------------------------------------------\n");

            for (ReceiptRequest r : list) {
                sb.append(String.format("%-14s | %15s | %-20s\n",
                        r.getDate(),
                        String.format("%,d원", r.getPrice()),
                        r.getMemo()));
            }

            sb.append("-------------------------------------------------------\n");
            sb.append(String.format("소계 합계      : %,d원\n\n", sum));
        }
        sb.append(String.format("%-15s-한눈에 지출-\n", "", email));
        // 콘솔 출력
        System.out.println(ConsoleStyle.apply("\n" + sb.toString(), ConsoleStyle.PURPLE));

        // 자동 저장
        String filename = String.format("%s-%s-카테고리보고서.txt", email, yearMonthStr);
        saveReportToFile(filename, sb.toString());
    }

    /** PrintWriter 사용해서 파일로 저장 */
    private void saveReportToFile(String filename, String content) throws IOException {
    	// 상대경로로 data/report 폴더 지정
        Path dir = Paths.get("data", "report");
        // 디렉토리가 없으면 생성
        Files.createDirectories(dir);
        // 저장할 파일 경로
        Path path = dir.resolve(filename);
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(path))) {
            pw.print(content);
        }
        System.out.println("보고서가 자동 저장되었습니다: " + filename);
    }
}
