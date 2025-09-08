package io.github.choimari.moomoney.views;


import java.util.HashMap;
import java.util.Map;

import io.github.choimari.moomoney.App;
import io.github.choimari.moomoney.controller.RegularController;
import io.github.choimari.moomoney.domain.Category;
import io.github.choimari.moomoney.factory.AbstractView;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.util.ConsoleStyle;
import io.github.choimari.moomoney.util.InputReader;
/**
 * 영수증 등록 화면
 */
public class ReceiptRegisterView extends AbstractView{
	private final App app;// 현재 접속 유저 정보 담은 객체
	private final RegularController regularController;
	
    // 생성자에서 User 전달
    public ReceiptRegisterView(App app, RegularController regularController) {
        this.app = app;
        this.regularController = regularController;
    }
    
	@Override
    protected void printHeader() {
		Map<String,String> receiptInfo = new HashMap<>();
        System.out.println("============================================");
        System.out.printf(ConsoleStyle.apply("<🧾 %s >\n\n", ConsoleStyle.BLUE), ViewType.RECEIPT_REGISTER.getType());
        receiptInfo.put("date",regularController.input("날짜 입력 (예)2025-09-09 : "));
        System.out.println();
        System.out.println("===👛 지출 카테고리===");
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("[%d] %s ", i+1, categories[i].getLabel());
        }
        System.out.println();
        receiptInfo.put("category",regularController.input("선택 : "));
        System.out.println();
        receiptInfo.put("price", regularController.input("💸금액 입력 : "));
        System.out.println();
        receiptInfo.put("memo", regularController.input("📝메모 : "));
        boolean success = regularController.registerReceipt(receiptInfo);// 컨트롤러에서 유효성 검사 후 등록처리함
        // 결과 안내
        if(success) {
            System.out.println(ConsoleStyle.apply("\n영수증 등록 완료 ✅\n", ConsoleStyle.GREEN));
        } else {
            System.out.println(ConsoleStyle.apply("\n영수증 등록 실패 ❌\n", ConsoleStyle.RED));
        }
    }

	@Override
	protected void showContent() {
		
		
	}
	@Override
    protected void printFooter() {
        System.out.println("============================================");
    }

}
