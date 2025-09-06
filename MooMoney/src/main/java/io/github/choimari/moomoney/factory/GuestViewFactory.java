package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.util.InputReader;
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
    
    protected GuestViewFactory(InputReader reader) {
		super(reader);
	}

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
    public View createView(ViewType viewType) {
        switch(viewType) {
        	case GUEST: return new GuestMenuView(reader);
            case MAIN: return new MainView(reader);
            case LOGIN: return new LoginView(reader);
            case SIGNUP: return new SignupView(reader);
            default: return null;
        }
    }

}
