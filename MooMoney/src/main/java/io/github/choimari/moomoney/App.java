package io.github.choimari.moomoney;

import io.github.choimari.moomoney.controller.BaseController;
import io.github.choimari.moomoney.controller.GuestController;
import io.github.choimari.moomoney.controller.MainController;
import io.github.choimari.moomoney.factory.GuestViewFactory;
import io.github.choimari.moomoney.factory.ViewAbstractFactory;
import io.github.choimari.moomoney.repository.CSVUserRepository;
import io.github.choimari.moomoney.repository.TXTUserRepository;
import io.github.choimari.moomoney.service.LoginService;
import io.github.choimari.moomoney.service.SignUpService;
import io.github.choimari.moomoney.util.InputReader;


/**
 * 프로그램 진입점
 * 여기서 전부 주입
 */
public class App { 
	public static void main(String[] args) {
		//싱글톤 패턴으로 입력 처리를 담당하는 객체 생성
		InputReader reader = InputReader.getInstance(); 
 
		// 게스트(비회원) 화면을 만드는 뷰 팩토리 객체
        ViewAbstractFactory guestFactory = new GuestViewFactory();
        LoginService loginService = new LoginService();
        TXTUserRepository tUserRepo = new TXTUserRepository();
        CSVUserRepository cUserRepo = new CSVUserRepository();
        SignUpService signUpService = new SignUpService(tUserRepo, cUserRepo);
        
        GuestController guestController = new GuestController(reader, guestFactory, loginService, signUpService);
        // 생성자 주입
        BaseController main = new MainController(reader, guestFactory, guestController);
        main.run();
	}
}
