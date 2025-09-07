package io.github.choimari.moomoney.controller;

import io.github.choimari.moomoney.factory.ViewAbstractFactory;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.View;

public class GuestController extends BaseController{
	private ViewAbstractFactory guestFactory;
	
	public GuestController(InputReader reader, ViewAbstractFactory guestFactory) {
		super(reader);
		this.guestFactory = guestFactory;
	}

	@Override
	public void run() {
	    View guestView = guestFactory.createView(ViewType.GUEST, this);
	    View loginView = guestFactory.createView(ViewType.LOGIN, this);
	    View signupView = guestFactory.createView(ViewType.SIGNUP, this);
               
        boolean running = true;
        while(running) {
        	guestView.show(); // 비 로그인 시 메뉴화면 출력
            String choice = input();
            switch(choice) {
                case "1": loginView.show(); break;
                case "2": signupView.show(); break;
                case "3": running = false; break;
                default: System.out.println("[입력 오류] : 다시 입력해 주세요.");
            }
        }  
	}
	
	public String input() {
		return reader.readLine("입력 : ");
	}
		
}
