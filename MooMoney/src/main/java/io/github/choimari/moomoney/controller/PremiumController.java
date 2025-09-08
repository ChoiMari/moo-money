package io.github.choimari.moomoney.controller;

import io.github.choimari.moomoney.App;
import io.github.choimari.moomoney.factory.ViewAbstractFactory;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.service.ReceiptService;
import io.github.choimari.moomoney.service.ReportService;
import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.View;

public class PremiumController extends BaseController{
	 private final App app;
	 private final ViewAbstractFactory regularViewFactory;
	 private final ReceiptService receiptSvc;
	 private final ViewAbstractFactory premiumViewFactory;
	 private final RegularController regularController;
	 private final ReportService reportSvc; // 보고서 서비스
	 
	 public PremiumController(InputReader reader, App app, ViewAbstractFactory regularViewFactory, ReceiptService receiptSvc,
			 ViewAbstractFactory premiumViewFactory, RegularController regularController,
			 ReportService reportSvc) {
	        super(reader);
	        this.app = app;
	        this.regularViewFactory = regularViewFactory;
	        this.receiptSvc = receiptSvc;
	        this.premiumViewFactory = premiumViewFactory;
	        this.regularController = regularController;
	        this.reportSvc = reportSvc;
	}
	@Override
	public void run() {
		boolean running = true;
        View menu = premiumViewFactory.createView(ViewType.PREMIUM, this);
        View register = regularViewFactory.createView(ViewType.RECEIPT_REGISTER, regularController);
        View search = regularViewFactory.createView(ViewType.RECEIPT_SEARCH, regularController);
        View report = premiumViewFactory.createView(ViewType.REPORT, this);
        menu.show();
        
        while (running) {
            menu.show(); // 프리미엄 메뉴 출력
            String choice = input("입력 : ");

            switch (choice) {
                case "1": // 영수증 등록
                	register.show();
                    break;
                case "2": // 전체 조회
                	search.show();
                    break;
                case "3": // 보고서 출력 (프리미엄 전용)
                	report.show();
                    break;
                case "4": // 로그아웃
                    System.out.println("로그아웃 하였습니다.");
                    app.setCurrentUser(null);
                    running = false;
                    break;
                default:
                    System.out.println("[입력 오류] 1~4 중에서 선택해주세요.");
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
	 * 연월보고서
	 */
	public void showMonthlyReport() {
	    String email = app.getCurrentUser().getEmail(); // 로그인 사용자 이메일
	    String yearMonth;

	    // 1. 연월 입력 + 검증
	    while (true) {
	        yearMonth = input("조회할 연월 입력 (예: 2025-09) : ").trim();

	        // yyyy-MM 형식 체크
	        if (!yearMonth.matches("\\d{4}-(0[1-9]|1[0-2])")) {
	            System.out.println("[입력 오류] 형식이 올바르지 않습니다. 예: 2025-09");
	            continue;
	        }

	        // 존재하는 달인지 확인
	        try {
	            java.time.YearMonth.parse(yearMonth, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM"));
	        } catch (Exception e) {
	            System.out.println("[입력 오류] 존재하지 않는 연월입니다.");
	            continue;
	        }

	        break; // 올바른 입력이면 반복 종료
	    }

	    // 2. 서비스 호출 → 화면 출력 + 파일 자동 저장
	    try {
	        reportSvc.showMonthlyReport(email, yearMonth);
	    } catch (Exception e) {
	        System.out.println("[ERROR] 월별 보고서 처리 실패: " + e.getMessage());
	    }
	}

	/**
	 * 카테고리별 보고서
	 */
	public void showCategoryReport() {
	    String email = app.getCurrentUser().getEmail(); // 로그인 사용자 이메일
	    String yearMonth;

	    // 연월 입력 + 검증
	    while (true) {
	        yearMonth = input("조회할 연월 입력 (예: 2025-09) : ").trim();
	        System.out.println();
	        if (!yearMonth.matches("\\d{4}-(0[1-9]|1[0-2])")) {
	            System.out.println("날짜 형식이 올바르지 않습니다. 예: 2025-09");
	            continue;
	        }

	        try {
	            java.time.YearMonth.parse(yearMonth, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM"));
	        } catch (Exception e) {
	            System.out.println("[입력 오류] 존재하지 않는 연월입니다.");
	            continue;
	        }

	        break;
	    }

	    // 서비스 호출
	    try {
	        reportSvc.showCategoryReport(email, yearMonth);
	    } catch (Exception e) {
	        System.out.println("[ERROR] 카테고리별 보고서 처리 실패: " + e.getMessage());
	    }
	}


}
