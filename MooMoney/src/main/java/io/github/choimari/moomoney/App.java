package io.github.choimari.moomoney;

import io.github.choimari.moomoney.controller.AdminController;
import io.github.choimari.moomoney.controller.BaseController;
import io.github.choimari.moomoney.controller.GuestController;
import io.github.choimari.moomoney.controller.MainController;
import io.github.choimari.moomoney.controller.PremiumController;
import io.github.choimari.moomoney.domain.User;
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
 * 로그인 상태 관리
 */
public class App { 
	private User currentUser; // 현재 로그인한 사용자 상태 관리
	private final InputReader reader;
    private final ViewAbstractFactory guestFactory;
    private final TXTUserRepository tUserRepo;
    private final CSVUserRepository cUserRepo;
    private final LoginService loginService;
    private final SignUpService signUpService;

    private final GuestController guestController;
    private final MainController mainController;
    
    // App 생성자에서 모든 의존성 주입
    public App() {
        this.reader = InputReader.getInstance();
        this.guestFactory = new GuestViewFactory();
        this.tUserRepo = new TXTUserRepository();
        this.cUserRepo = new CSVUserRepository();
        this.loginService = new LoginService(tUserRepo);
        this.signUpService = new SignUpService(tUserRepo, cUserRepo);
        this.guestController = new GuestController(reader, guestFactory, loginService, signUpService, this);
        this.mainController = new MainController(reader, guestFactory, guestController);
    }
    
    // 로그인 성공 시 GuestController에서 호출 → App에 상태 저장
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public User getCurrentUser() {
        return this.currentUser;
    }
    
    public void run() {
        mainController.run();
    }
    
	public static void main(String[] args) {
        App app = new App(); // 초기화는 App 내부에서 전부 처리
        app.run();           // 실행만 시작

	}
}
