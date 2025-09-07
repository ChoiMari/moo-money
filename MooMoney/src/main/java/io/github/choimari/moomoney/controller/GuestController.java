package io.github.choimari.moomoney.controller;

import java.io.IOException;
import java.util.Map;

import io.github.choimari.moomoney.domain.Role;
import io.github.choimari.moomoney.dto.LoginRequest;
import io.github.choimari.moomoney.dto.SignUpRequest;
import io.github.choimari.moomoney.factory.ViewAbstractFactory;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.repository.TXTUserRepository;
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
	 * 유효성 검사 (회원가입 처리에 필요)
	 * @param info Map<필드명, 사용자 입력값>
	 * @return true: 모두 유효, false: 하나라도 유효하지 않음
	 */
	public boolean validationAndDuplicate(Map<String, String> info) {
	    String email = info.get("email");
	    String pw = info.get("pw");
	    String ckpw = info.get("ckpw");
	    String nickname = info.get("nickname");
	    String roleChoice= info.get("role");

	    boolean valid = true;

	    // 이메일 유효성 체크
	    if (!signUpSvc.validationEmail(email)) {
	        System.out.println("이메일 형식이 올바르지 않습니다.");
	        valid = false;
	    }
	    
	    // 이메일 중복 검사
	    if(signUpSvc.checkEmailDuplicate(email)) { // 중복이면 true
	    	System.out.println("이미 사용중인 이메일 입니다.");
	    	valid = false;
	    }
	    
	    // 비밀번호 유효성 체크
	    if (!signUpSvc.validationPassword(pw)) {
	        System.out.println("비밀번호는 최소 8자리, 영문+숫자+특수문자(@#$%^&+=!) 조합이어야 합니다.");
	        valid = false;
	    }

	    // 비밀번호 확인
	    if (!pw.equals(ckpw)) {
	        System.out.println("비밀번호와 확인이 일치하지 않습니다.");
	        valid = false;
	    }

	    // 닉네임 유효성 체크
	    if (!signUpSvc.validationNickname(nickname)) {
	        System.out.println("닉네임은 2~12자, 한글/영문/숫자만 가능합니다.");
	        valid = false;
	    }
	    
	    //닉네임 중복 검사
	    if(signUpSvc.checkNicknameDuplicate(nickname)) { // 중복이면 실행
	    	System.out.println("이미 사용중인 닉네임 입니다.");
	    	valid = false;
	    }

	    // 회원 등급
	    Role role = null;;
	    switch (roleChoice) {
	        case "1": role = Role.REGULAR_MEMBER; break;
	        case "2": role = Role.PREMIUM_MEMBER; break;
	        default:
	            System.out.println("[오류] 회원 등급은 1(일반) 또는 2(프리미엄)만 가능합니다.");
	            valid = false;
	    }

	    if(valid) {
	        // 모두 통과 시 실제 회원가입 서비스 호출
	    	//dto 객체 생성 -> 서비스계층으로 전달 
	    	// 서비스에서 -> User객체로 변환 -> 레파지토리에서 DB또는 파일로 저장처리(지금은 파일)
	        SignUpRequest dto = new SignUpRequest(email, pw, role, nickname);
	        try {
				signUpSvc.signUp(dto); // 서비스에서 DB 저장 또는 파일 저장 처리
			} catch (IOException e) {
				System.out.println("[서버 오류] 다음에 다시 시도해주세요.");
				e.printStackTrace();
			} 
	    }

	    return valid;
	}
}
