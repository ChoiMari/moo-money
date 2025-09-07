package io.github.choimari.moomoney.controller;

import java.util.Map;

import io.github.choimari.moomoney.dto.LoginRequest;
import io.github.choimari.moomoney.factory.ViewAbstractFactory;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.service.LoginService;
import io.github.choimari.moomoney.service.SignUpService;
import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.View;
/**
 * 로그인 전 “손님” 입장에서 단순 메뉴/화면 이동 담당
 */
public class GuestController extends BaseController{
	private final ViewAbstractFactory guestFactory;
	private final LoginService loginSvc;
	private final SignUpService signUpSvc;
	
	public GuestController(InputReader reader, ViewAbstractFactory guestFactory, LoginService loginSvc, SignUpService signUpSvc) {
		super(reader);
		this.guestFactory = guestFactory;
		this.signUpSvc = signUpSvc;
		this.loginSvc = loginSvc;
	}

	@Override
	public void run() {
	    View guestView = guestFactory.createView(ViewType.GUEST, this);
	    View loginView = guestFactory.createView(ViewType.LOGIN, this);
	    View signupView = guestFactory.createView(ViewType.SIGNUP, this);
               
        boolean running = true;
        while(running) {
        	guestView.show(); // 비 로그인 시 메뉴화면 출력
            String choice = input("입력 : ");
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
	public String input(String prompt) {
		return reader.readLine(prompt);
	}
	
	/**
	 * 로그인
	 * @return 결과
	 */
	public boolean login(LoginRequest dto) {
		return false;
	}
	
	/**
	 * 회원 가입 
	 */
	
	/**
	 * 유효성 검사
	 */
	public boolean validation(Map<String, String> info) {
		
		return false;
	}
}
