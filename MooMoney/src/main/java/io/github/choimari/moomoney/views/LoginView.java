package io.github.choimari.moomoney.views;

import io.github.choimari.moomoney.factory.AbstractView;
import io.github.choimari.moomoney.factory.ViewType;
public class LoginView extends AbstractView{

	
	
    protected void printHeader() {
        System.out.printf("\n<%s>", ViewType.LOGIN.getType());
    }
	
	@Override
	protected void showContent() {
		System.out.println();
		System.out.println("아이디 : ");
		// 컨트롤러 호출
		System.out.println("비밀번호 : ");
	}

    protected void printFooter() {
        System.out.println("============================================");
    }

}
