package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.controller.AdminController;
import io.github.choimari.moomoney.controller.GuestController;
import io.github.choimari.moomoney.controller.PremiumController;
import io.github.choimari.moomoney.controller.RegularController;
import io.github.choimari.moomoney.util.InputReader;
/**
 * 컨트롤러 추상 팩토리 인터페이스
 * - 권한별 컨트롤러 객체 생성 메서드 정의
 * - 각 메서드는 구체 팩토리에서 구현
 */
public interface ControllerAbstractFactory {
	    /**
	     * 게스트용 컨트롤러 생성
	     * @param reader 사용자 입력 Reader
	     * @param viewFactory 뷰 생성 추상팩토리
	     * @return GuestController 생성된 게스트 컨트롤러
	     */
	    GuestController createGuestController(InputReader reader, ViewAbstractFactory viewFactory);
	    
	    /**
	     * 일반회원용 컨트롤러 생성
	     * @param reader 사용자 입력 Reader
	     * @return RegularController 생성된 일반회원 컨트롤러
	     */
	    RegularController createRegularController(InputReader reader);
	    
	    /**
	     * 프리미엄회원용 컨트롤러 생성
	     * @param reader 사용자 입력 Reader
	     * @return PremiumController 생성된 프리미엄회원 컨트롤러
	     */
	    PremiumController createPremiumController(InputReader reader);
	    
	    /**
	     * 관리자용 컨트롤러 생성
	     * @param reader 사용자 입력 Reader
	     * @return AdminController 생성된 관리자 컨트롤러
	     */
	    AdminController createAdminController(InputReader reader);
	    
	    /**
	     * TODO: 시스템용 컨트롤러 생성 메서드 정의
	     * - 추후 SystemController 필요 시 추가
	     */
	    
	    // SystemController createSystemController(InputReader reader);
}
