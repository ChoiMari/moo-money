package io.github.choimari.moomoney.views;

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
	    String id = guestController.input("이메일 : "); // 입력값 받음
	    String password = guestController.input("비밀번호 : ");

	    // 원래 Map으로 받을까 하다가.. dto..일이 점점 커진다.. 이건 그냥 작은 콘솔 프로그램인데..
	    LoginRequest loginRequest = new LoginRequest(id, password);
	    if(guestController.login(loginRequest) != null) {
	    	System.out.println(ConsoleStyle.apply("\n🌼로그인에 성공했습니다🌼", ConsoleStyle.GREEN));
        } else {
            System.out.println(ConsoleStyle.apply("\n[로그인 실패] 이메일 또는 비밀번호가 일치하지 않습니다.", ConsoleStyle.ITALIC));
        }
	}
	@Override
    protected void printFooter() {
        System.out.println();
    }

}
