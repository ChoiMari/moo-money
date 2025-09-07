package io.github.choimari.moomoney.views;

import java.util.HashMap;
import java.util.Map;

import io.github.choimari.moomoney.controller.GuestController;
import io.github.choimari.moomoney.dto.LoginRequest;
import io.github.choimari.moomoney.factory.AbstractView;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.util.ConsoleStyle;
public class LoginView extends AbstractView{
	
	private final GuestController guestController;
	
	public LoginView(GuestController guestController) {
		this.guestController = guestController;
	}
	@Override
    protected void printHeader() {
    	System.out.println("============================================");
        System.out.printf(ConsoleStyle.apply("<🔐 %s>\n\n", ConsoleStyle.BLUE), ViewType.LOGIN.getType());
    }
	
	@Override
	protected void showContent() {
	    String id = guestController.input("아이디 : "); // 입력값 받음
	    String password = guestController.input("비밀번호 : ");

	    // 원래 Map으로 받을까 하다가.. dto..일이 점점 커진다.. 이건 그냥 작은 콘솔 프로그램인데..
	    LoginRequest loginRequest = new LoginRequest(id, password);
	    guestController.login(loginRequest);
	}
	@Override
    protected void printFooter() {
        System.out.println("============================================");
    }

}
