package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.App;
import io.github.choimari.moomoney.controller.AdminController;
import io.github.choimari.moomoney.controller.GuestController;
import io.github.choimari.moomoney.controller.PremiumController;
import io.github.choimari.moomoney.controller.RegularController;
import io.github.choimari.moomoney.repository.ReceiptRepository;
import io.github.choimari.moomoney.service.LoginService;
import io.github.choimari.moomoney.service.ReceiptService;
import io.github.choimari.moomoney.service.SignUpService;
import io.github.choimari.moomoney.util.InputReader;

/**
 * 구체 컨트롤러 팩토리 (Concrete Factory)
 * 추상 팩토리(ControllerAbstractFactory) 구현체
 * - 각 컨트롤러 생성 시 필요한 서비스 객체 주입
 * App.java에서 DI를 통해 서비스 단일 인스턴스를 전달
 */
public class ConcreteControllerFactory  implements ControllerAbstractFactory {
    
	private final InputReader reader;
    private final ViewAbstractFactory viewFactory;
    private final App app;
    private final LoginService loginSvc; // 로그인 처리 서비스
    private final SignUpService signUpSvc; // 회원가입 처리 서비스
    private final ReceiptService receiptSvc;

    /**
     * 생성자 주입(Constructor DI)
     * - App.java에서 객체 전달
     */
    public ConcreteControllerFactory( LoginService loginSvc,
            SignUpService signUpSvc,
            InputReader reader,
            ViewAbstractFactory viewFactory,
            App app,
            ReceiptService receiptSvc) {
        this.reader = reader;
        this.viewFactory = viewFactory;
        this.app = app;
        this.loginSvc = loginSvc;
        this.signUpSvc = signUpSvc;
        this.receiptSvc = receiptSvc;
    }

    /**
     * 게스트 컨트롤러 생성
     * - 로그인 서비스와 회원가입 서비스 주입
     */
    @Override
    public GuestController createGuestController() {
        return new GuestController(reader, viewFactory, loginSvc, signUpSvc, app);
    }

    /**
     * 일반회원 컨트롤러 생성
     * - 현재 서비스 필요 없음
     */
    @Override
    public RegularController createRegularController() {
        return new RegularController(reader, app, viewFactory, receiptSvc);
    }

    /**
     * 프리미엄회원 컨트롤러 생성
     * - 현재 서비스 필요 없음
     */
    @Override
    public PremiumController createPremiumController() {
        return new PremiumController(reader);
    }

    /**
     * 관리자 컨트롤러 생성
     * - 현재 서비스 필요 없음
     */
    @Override
    public AdminController createAdminController() {
        return new AdminController(reader);
    }

}
