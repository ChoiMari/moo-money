package io.github.choimari.moomoney.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import io.github.choimari.moomoney.App;
import io.github.choimari.moomoney.domain.Category;
import io.github.choimari.moomoney.domain.User;
import io.github.choimari.moomoney.dto.ReceiptRequest;
import io.github.choimari.moomoney.factory.ViewAbstractFactory;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.service.ReceiptService;
import io.github.choimari.moomoney.util.ConsoleStyle;
import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.RegularMenuView;
import io.github.choimari.moomoney.views.View;

public class RegularController extends BaseController {
	 private final App app;
	 private final ViewAbstractFactory regularViewFactory;
	 private final ReceiptService receiptSvc;
	 
	 public RegularController(InputReader reader, App app, ViewAbstractFactory regularViewFactory, ReceiptService receiptSvc) {
	        super(reader);
	        this.app = app;
	        this.regularViewFactory = regularViewFactory;
	        this.receiptSvc = receiptSvc;
	    }

	@Override
	public void run() {
		boolean running = true;
        View menuView = regularViewFactory.createView(ViewType.REGULAR, this);
        View registerView = regularViewFactory.createView(ViewType.RECEIPT_REGISTER, this);
        View searchView = regularViewFactory.createView(ViewType.RECEIPT_SEARCH, this);
        while (running) {
            menuView.show();  // 화면 출력
            String choice = input("입력 : ");
            switch (choice) {
                case "1":
                	registerView.show();// 영수증 등록 로직
                    break;
                case "2":
                	searchView.show();// 영수증 조회 로직
                    break;
                case "3":
                	System.out.println(ConsoleStyle.apply("\n로그아웃 하였습니다.", ConsoleStyle.PURPLE));
                    app.setCurrentUser(null); // 로그아웃
                    running = false;
                    break;
                default:
                    System.out.println("[입력 오류] 다시 입력해주세요.");
            }
        }
	}

	/**
	 * View에서 호출해서 리턴받는 용도
	 * @return 사용자 입력값
	 */
	public String input(String prompt) {
		return reader.readLine(prompt);
	}
	
	/**
     * 날짜 유효성 검사
     * - yyyy-MM-dd 형식 체크
     * - 미래 날짜 입력 금지
     */
    public LocalDate validateDate(String dateStr) {
    	try {
            LocalDate date = LocalDate.parse(dateStr);
            if (date.isAfter(LocalDate.now())) { // 미래 날짜 체크
                System.out.println("미래 날짜는 입력할 수 없습니다.");
                return null;
            }
            return date;
        } catch (DateTimeParseException e) {
            System.out.println("날짜 형식이 올바르지 않습니다. yyyy-MM-dd 형식으로 입력하세요.");
            return null;
        }
    }

    /**
     * 카테고리 유효성 검사
     */
    public Category validateCategory(String categoryIndexStr) {
        try {
            int index = Integer.parseInt(categoryIndexStr) - 1;
            Category[] categories = Category.values();
            if (index < 0 || index >= categories.length) {
                System.out.println("카테고리 선택이 올바르지 않습니다.");
                return null;
            }
            return categories[index];
        } catch (NumberFormatException e) {
            System.out.println("카테고리는 숫자로 입력해주세요.");
            return null;
        }
    }

    /**
     * 금액 유효성 검사
     */
    public Integer validatePrice(String priceStr) {
        try {
            int price = Integer.parseInt(priceStr);
            if (price < 0) {
                System.out.println("금액은 0 이상의 숫자여야 합니다.");
                return null;
            }
            return price;
        } catch (NumberFormatException e) {
            System.out.println("금액은 양의 정수로 입력해야 합니다.");
            return null;
        }
    }

    /**
     * 메모 유효성 검사 (길이 체크 등)
     */
    public String validateMemo(String memo) {
        if (memo == null || memo.trim().isEmpty()) {
            System.out.println("메모는 비워둘 수 없습니다.");
            return null;
        }
        return memo.trim();
    }
    
    /**
     * 영수증 등록
     * Map 데이터를 DTO로 변환 후 서비스에 전달
     */
    public boolean registerReceipt(Map<String, String> receiptInfo) {
        LocalDate date = validateDate(receiptInfo.get("date"));
        if (date == null) return false;

        Category category = validateCategory(receiptInfo.get("category"));
        if (category == null) return false;

        Integer price = validatePrice(receiptInfo.get("price"));
        if (price == null) return false;

        String memo = validateMemo(receiptInfo.get("memo"));
        if (memo == null) return false;

        // DTO 생성
        ReceiptRequest dto = new ReceiptRequest(date, category, price, memo);
        return receiptSvc.saveReceipt(dto, app.getCurrentUser().getEmail()); // 영수증 등록하고 결과반환
    }
    
    // ==========================
    // 영수증 조회 기능
    // ==========================
    /**
     * 연월 유효성 검사
     * - "2025-09" 형식 체크
     */
    public String validateYearMonth(String input) {
        try {
            String[] parts = input.split("-");
            if (parts.length != 2) throw new IllegalArgumentException();
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            if (month < 1 || month > 12) throw new IllegalArgumentException();
            return String.format("%04d-%02d", year, month); // 입력값 포맷 보정
        } catch (Exception e) {
            System.out.println(ConsoleStyle.apply("\n연월 형식이 올바르지 않습니다. 예: 2025-09\n", ConsoleStyle.DARK_GREY, ConsoleStyle.ITALIC));
            return null;
        }
    } 

    /** 전체 조회 */
    public void viewAllReceipts() {
        Map<Integer, Map<String, String>> receipts =
                receiptSvc.findAllByUser(app.getCurrentUser().getEmail());
        paginateMap(receipts, 5); // 5개씩 페이지네이션
    }

    /** 카테고리별 조회 */
    public void viewReceiptsByCategory() {
        System.out.println("=== 카테고리 선택 ===");
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("[%d] %s ", i + 1, categories[i].getLabel());
        }
        System.out.println();

        String inputVal = input("선택 : ");
        Category category = validateCategory(inputVal);
        if (category == null) return;

        Map<Integer, Map<String, String>> receipts =
                receiptSvc.findByCategory(app.getCurrentUser().getEmail(), category);
        paginateMap(receipts, 5);
    }

    /** 월별 조회 */
    public void viewReceiptsByMonth() {
        String yearMonth;
        while (true) {
            yearMonth = input("조회할 연월 입력 (예: 2025-09) : ");
            yearMonth = validateYearMonth(yearMonth);
            if (yearMonth != null) break; // 올바른 입력이면 탈출
        }

        Map<Integer, Map<String, String>> receipts =
                receiptSvc.findByMonth(app.getCurrentUser().getEmail(), yearMonth);
        paginateMap(receipts, 5);
    }


    /** 월별 + 카테고리별 조회 */
    public void viewReceiptsByMonthAndCategory() {
        String yearMonth = input("조회할 연월 입력 (예: 2025-09) : ");

        System.out.println("=== 카테고리 선택 ===");
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("[%d] %s ", i + 1, categories[i].getLabel());
        }
        System.out.println();

        String inputVal = input("선택 : ");
        Category category = validateCategory(inputVal);
        if (category == null) return;

        Map<Integer, Map<String, String>> receipts =
                receiptSvc.findByMonthAndCategory(app.getCurrentUser().getEmail(), yearMonth, category);
        paginateMap(receipts, 5);
    }

    // ==========================
    // 페이지네이션 출력
    // ==========================

    private void paginateMap(Map<Integer, Map<String, String>> receipts, int pageSize) {
        if (receipts.isEmpty()) {
            System.out.println(ConsoleStyle.apply("\n조회 결과가 없습니다.", ConsoleStyle.DARK_GREY, ConsoleStyle.ITALIC));
            return;
        }

        int total = receipts.size();
        int totalPages = (int) Math.ceil((double) total / pageSize);
        int currentPage = 1;

        while (true) {
            System.out.printf("\n=== 페이지 %d/%d ===\n", currentPage, totalPages);

            int start = (currentPage - 1) * pageSize + 1;
            int end = Math.min(currentPage * pageSize, total);

            for (int i = start; i <= end; i++) {
                Map<String, String> row = receipts.get(i);
                System.out.printf("%d. [%s] %s원 (%s) - %s\n",
                        i,
                        row.get("date"),
                        row.get("price"),
                        row.get("category"),
                        row.get("memo"));
            }

            if (currentPage >= totalPages) {
                break;
            }

            String next;
            while (true) {
                next = input("\n다음 페이지 보시겠습니까? (y/n) : ").trim().toLowerCase();
                if (next.equals("y") || next.equals("n")) break; // 유효한 입력만 탈출
                System.out.println(ConsoleStyle.apply("\n[입력 오류] y 또는 n만 입력 가능합니다.\n", ConsoleStyle.DARK_GREY, ConsoleStyle.ITALIC));
            }

            if (next.equals("y")) {
                currentPage++;
            } else {
                break;
            }
        }
    }



	
}
