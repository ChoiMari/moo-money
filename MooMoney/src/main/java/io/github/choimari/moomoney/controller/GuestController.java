package io.github.choimari.moomoney.controller;

import io.github.choimari.moomoney.dto.LoginRequest;
import io.github.choimari.moomoney.factory.ViewAbstractFactory;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.View;

public class GuestController extends BaseController{
	private final ViewAbstractFactory guestFactory;
	
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
                case "1": loginView.show(); break; // 로그인 선택
                case "2": signupView.show(); break; // 회원가입 선택
                case "3": running = false; break; // 이전 메뉴
                default: System.out.println("[입력 오류] : 다시 입력해 주세요.");
            }
        }  
	}
	
	/**
	 * View에서 호출해서 리턴받는 용도
	 * @return 사용자 입력값
	 */
	public String input() {
		return reader.readLine("입력 : ");
	}
	
	/**
	 * 로그인
	 * @return 결과
	 */
	public boolean login(LoginRequest dto) {
		return false;
	}
		
}
