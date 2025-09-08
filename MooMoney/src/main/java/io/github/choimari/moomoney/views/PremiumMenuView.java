package io.github.choimari.moomoney.views;

import io.github.choimari.moomoney.App;
import io.github.choimari.moomoney.controller.RegularController;
import io.github.choimari.moomoney.factory.AbstractView;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.util.ConsoleStyle;
import io.github.choimari.moomoney.util.InputReader;

public class PremiumMenuView extends AbstractView{
	private final App app;// 현재 접속 유저 정보 담은 객체
	
    // 생성자에서 User 전달
    public PremiumMenuView(App app) {
        this.app = app;
    }

	@Override
    protected void printHeader() {
		System.out.println("============================================");
        System.out.printf(ConsoleStyle.apply("< 접속자 : "+ app.getCurrentUser().getNickname() + ">\n", ConsoleStyle.CYAN)); 
    }

	@Override
	protected void showContent() {
        System.out.println("\n[1] 영수증 등록\n[2] 영수증 조회\n[3]보고서 출력\n[4] 로그아웃\n");
        System.out.println("원하시는 메뉴를 선택 해주세요.");
	}
	@Override
    protected void printFooter() {
        System.out.println("============================================");
    }

}
