package io.github.choimari.moomoney.controller;

import io.github.choimari.moomoney.App;
import io.github.choimari.moomoney.factory.PremiumViewFactory;
import io.github.choimari.moomoney.factory.ViewAbstractFactory;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.service.ReceiptService;
import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.View;

public class PremiumController extends BaseController{
	 private final App app;
	 private final ViewAbstractFactory regularViewFactory;
	 private final ReceiptService receiptSvc;
	 private final ViewAbstractFactory premiumViewFactory;
	 private final RegularController regularController;
	 
	 public PremiumController(InputReader reader, App app, ViewAbstractFactory regularViewFactory, ReceiptService receiptSvc,
			 ViewAbstractFactory premiumViewFactory, RegularController regularController) {
	        super(reader);
	        this.app = app;
	        this.regularViewFactory = regularViewFactory;
	        this.receiptSvc = receiptSvc;
	        this.premiumViewFactory = premiumViewFactory;
	        this.regularController = regularController;
	}
	@Override
	public void run() {
		boolean running = true;
        View menu = premiumViewFactory.createView(ViewType.PREMIUM, this);
        menu.show();
        
        while (running) {
            menu.show(); // 프리미엄 메뉴 출력
            String choice = input("입력 : ");

            switch (choice) {
                case "1": // 영수증 등록
                    regularViewFactory.createView(ViewType.RECEIPT_REGISTER, regularController).show();
                    break;
                case "2": // 전체 조회
                    regularViewFactory.createView(ViewType.RECEIPT_SEARCH, regularController).show();
                    break;
                case "3": // 보고서 출력 (프리미엄 전용)
                    //handleReportMenu();
                    break;
                case "4": // 로그아웃
                    System.out.println("로그아웃 하였습니다.🖐🏻");
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

}
