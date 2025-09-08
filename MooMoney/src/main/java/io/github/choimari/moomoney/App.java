package io.github.choimari.moomoney;

import io.github.choimari.moomoney.controller.AdminController;
import io.github.choimari.moomoney.controller.BaseController;
import io.github.choimari.moomoney.controller.GuestController;
import io.github.choimari.moomoney.controller.MainController;
import io.github.choimari.moomoney.controller.PremiumController;
import io.github.choimari.moomoney.controller.RegularController;
import io.github.choimari.moomoney.controller.SystemController;
import io.github.choimari.moomoney.domain.User;
import io.github.choimari.moomoney.factory.GuestViewFactory;
import io.github.choimari.moomoney.factory.RegularViewFactory;
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
    private final ViewAbstractFactory regularFactory;
    private final TXTUserRepository tUserRepo;
    private final CSVUserRepository cUserRepo;
    private final LoginService loginService;
    private final SignUpService signUpService;

    private final GuestController guestController;
    private final MainController mainController;
    private final RegularController regularController;
    private final PremiumController premiumController;
    private final AdminController adminController;
    private final SystemController systemController;
    
    // App 생성자에서 모든 의존성 주입
    public App() {
        this.reader = InputReader.getInstance();
        this.guestFactory = new GuestViewFactory();
        this.regularFactory = new RegularViewFactory(this);
        this.tUserRepo = new TXTUserRepository();
        this.cUserRepo = new CSVUserRepository();
        this.loginService = new LoginService(tUserRepo);
        this.signUpService = new SignUpService(tUserRepo, cUserRepo);
        this.guestController = new GuestController(reader, guestFactory, loginService, signUpService, this);
        this.mainController = new MainController(reader, guestFactory, guestController);
        this.regularController = new RegularController(reader, this, regularFactory);
        this.premiumController = new PremiumController(reader);
        this.adminController = new AdminController(reader);
        this.systemController = new SystemController();
    }
    
    // 로그인 성공 시 GuestController에서 호출 → App에 상태 저장
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public User getCurrentUser() {
        return this.currentUser;
    }
    
    public void run() {
       // mainController.run();
        boolean running = true;
        while (running) {
            if (currentUser == null) { // 로그아웃 상태
            	mainController.run(); // 메인메뉴
                if (mainController.getExitRequested()) { // 종료 요청 확인
                    running = false;
                }
                //guestController.run(); // 로그인/회원가입 처리
            } else {
                // 로그인 성공 후 권한별 컨트롤러 호출
                switch (currentUser.getRole()) {
                    case REGULAR_MEMBER : regularController.run(); 
                    	break;
                    case PREMIUM_MEMBER : premiumController.run();
                    	break;
                    case ADMIN : adminController.run();
                    	break;
                    default : System.out.println("[ERROR] 알 수 없는 권한입니다.");
                }
            }
        }
    }
    
	public static void main(String[] args) {
        App app = new App(); // 초기화는 App 내부에서 전부 처리
        app.run();           // 실행만 시작

	}
}
