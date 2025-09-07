package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.controller.AdminController;
import io.github.choimari.moomoney.controller.GuestController;
import io.github.choimari.moomoney.controller.PremiumController;
import io.github.choimari.moomoney.controller.RegularController;
import io.github.choimari.moomoney.service.LoginService;
import io.github.choimari.moomoney.service.SignUpService;
import io.github.choimari.moomoney.util.InputReader;

/**
 * 구체 컨트롤러 팩토리 (Concrete Factory)
 * 추상 팩토리(ControllerAbstractFactory) 구현체
 * - 각 컨트롤러 생성 시 필요한 서비스 객체 주입
 * App.java에서 DI를 통해 서비스 단일 인스턴스를 전달
 */
public class ConcreteControllerFactory  implements ControllerAbstractFactory {

    private final LoginService loginSvc; // 로그인 처리 서비스
    private final SignUpService signUpSvc; // 회원가입 처리 서비스

    /**
     * 생성자 주입(Constructor DI)
     * - App.java에서 싱글톤/공유 서비스 객체 전달
     * @param loginSvc 로그인 서비스
     * @param signUpSvc 회원가입 서비스
     */
    public ConcreteControllerFactory(LoginService loginSvc, SignUpService signUpSvc) {
        this.loginSvc = loginSvc;
        this.signUpSvc = signUpSvc;
    }

    /**
     * 게스트 컨트롤러 생성
     * - 로그인 서비스와 회원가입 서비스 주입
     */
    @Override
    public GuestController createGuestController(InputReader reader, ViewAbstractFactory viewFactory) {
        return new GuestController(reader, viewFactory, loginSvc, signUpSvc);
    }

    /**
     * 일반회원 컨트롤러 생성
     * - 현재 서비스 필요 없음
     */
    @Override
    public RegularController createRegularController(InputReader reader) {
        return new RegularController(reader);
    }

    /**
     * 프리미엄회원 컨트롤러 생성
     * - 현재 서비스 필요 없음
     */
    @Override
    public PremiumController createPremiumController(InputReader reader) {
        return new PremiumController(reader);
    }

    /**
     * 관리자 컨트롤러 생성
     * - 현재 서비스 필요 없음
     */
    @Override
    public AdminController createAdminController(InputReader reader) {
        return new AdminController(reader);
    }

}
