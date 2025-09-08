package io.github.choimari.moomoney.views;

import java.util.HashMap;
import java.util.Map;

import io.github.choimari.moomoney.App;
import io.github.choimari.moomoney.controller.RegularController;
import io.github.choimari.moomoney.factory.AbstractView;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.util.ConsoleStyle;


/**
 * [영수증 조회 화면]
 * - 전체 조회
 * - 카테고리별 조회
 * - 월별 조회
 * 사용자의 선택을 받아 RegularController의 조회 메서드를 호출
 */
public class ReceiptSearchView extends AbstractView{
	private final App app;// 현재 접속 유저 정보 담은 객체
	private final RegularController regularController;
	
    // 생성자에서 User 전달
    public ReceiptSearchView(App app, RegularController regularController) {
        this.app = app;
        this.regularController = regularController;
    }
    
	@Override
    protected void printHeader() {
        System.out.println("============================================");
        System.out.printf(ConsoleStyle.apply("< %s >\n\n", ConsoleStyle.BLUE), ViewType.RECEIPT_SEARCH.getType());
    }
	
	@Override
	protected void showContent() {
		 System.out.println("[1] 전체 조회");
		 System.out.println("[2] 카테고리별 조회");
		 System.out.println("[3] 월별 조회");
		 String choice = regularController.input("선택 : ");
	        switch (choice) {
            case "1": // 전체 조회
                System.out.println(ConsoleStyle.apply("\n[ 전체 영수증 조회 ]\n", ConsoleStyle.PURPLE));
                regularController.viewAllReceipts();
                break;

            case "2": // 카테고리별 조회
                System.out.println(ConsoleStyle.apply("\n[ 카테고리별 영수증 조회 ]\n", ConsoleStyle.PURPLE));
                regularController.viewReceiptsByCategory();
                break;

            case "3": // 월별 조회
                System.out.println(ConsoleStyle.apply("\n[ 월별 영수증 조회 ]\n", ConsoleStyle.PURPLE));
                regularController.viewReceiptsByMonth();
                break;

            default:
                System.out.println(ConsoleStyle.apply("\n잘못된 입력입니다.", ConsoleStyle.ITALIC, ConsoleStyle.DARK_GREY));
        }
	}
	
	@Override
    protected void printFooter() {
        System.out.println();
    }

}
