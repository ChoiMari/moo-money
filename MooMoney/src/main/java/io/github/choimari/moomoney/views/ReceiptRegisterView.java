package io.github.choimari.moomoney.views;

import io.github.choimari.moomoney.App;
import io.github.choimari.moomoney.controller.RegularController;
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
        System.out.println("============================================");
        System.out.printf(ConsoleStyle.apply("<🧾 %s >\n\n", ConsoleStyle.BLUE), ViewType.RECEIPT_REGISTER.getType());
        regularController.input("");
    }

	@Override
	protected void showContent() {
		
		
	}
	@Override
    protected void printFooter() {
        System.out.println("============================================");
    }

}
