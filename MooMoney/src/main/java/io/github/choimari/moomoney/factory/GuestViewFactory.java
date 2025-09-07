package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.controller.BaseController;
import io.github.choimari.moomoney.controller.GuestController;
import io.github.choimari.moomoney.views.GuestMenuView;
import io.github.choimari.moomoney.views.LoginView;
import io.github.choimari.moomoney.views.MainView;
import io.github.choimari.moomoney.views.SignupView;
import io.github.choimari.moomoney.views.View;
/**
 * 게스트(비회원) 전용 뷰를 생성하는 구체 팩토리 클래스
 * InputReader를 주입 받아서 생성되는 뷰에 전달
 * ViewType enum에 따라 어떤 화면(View) 객체를 생성할지 결정
 */
public class GuestViewFactory extends ViewAbstractFactory{

	/**
     * View 객체 생성 메서드
     * @param viewType 생성할 뷰 타입(enum)
     * @return View 객체 (알 수 없는 타입이면 null 반환)
     * 
     * - GUEST : 비회원 메뉴 화면
     * - MAIN  : 메인 화면
     * - LOGIN : 로그인 화면
     * - SIGNUP: 회원가입 화면
     */
	@Override
    public View createView(ViewType viewType, BaseController baseController) {// TODO : instanceof로 타입체크 추가하기
        switch(viewType) {
        	case GUEST: return new GuestMenuView((GuestController) baseController);
            case MAIN: return new MainView(); // 여기는 입력받는 로직이 없어서 필요없음
            case LOGIN: return new LoginView((GuestController) baseController);
            case SIGNUP: return new SignupView((GuestController) baseController);
            default: return null;
        }
    }

}
