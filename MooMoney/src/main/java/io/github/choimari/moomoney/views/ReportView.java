package io.github.choimari.moomoney.views;

import java.time.YearMonth;
import java.util.List;


import io.github.choimari.moomoney.controller.PremiumController;
import io.github.choimari.moomoney.domain.Category;
import io.github.choimari.moomoney.dto.ReceiptRequest;
import io.github.choimari.moomoney.factory.AbstractView;
import io.github.choimari.moomoney.util.ConsoleStyle;
import io.github.choimari.moomoney.util.InputReader;


public class ReportView extends AbstractView{
	private final PremiumController premiumController;
	
	public ReportView(PremiumController premiumController) {
		this.premiumController = premiumController;
	}

	@Override
    protected void printHeader() {
		System.out.println("============================================");
		System.out.println(ConsoleStyle.apply("\n<보고서 출력>", ConsoleStyle.YELLOW));
    }

	@Override
	protected void showContent() {
        System.out.println("\n[1] 월별 전체 보고서 \n[2] 월별 카테고리별 보고서\n[3] 이전 메뉴\n");
        System.out.println("원하시는 메뉴를 선택 해주세요.");
        System.out.println("============================================");
        String choice = premiumController.input("선택 : ");
        System.out.println();
        switch (choice) {
	        case "1":
	        	premiumController.showMonthlyReport(); // 월별 지출 보고서 출력
	            break;
	        case "2":
	        	premiumController.showCategoryReport();
	            break;
	        case "3":
	            return; // 이전 메뉴
	        default:
	            System.out.println("1~3 중에서 선택해주세요.");
	    }
        
	}
	@Override
    protected void printFooter() {
        System.out.println("");
    }
	
}
