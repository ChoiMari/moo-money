package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.MainView;
import io.github.choimari.moomoney.views.View;
/**
 * View 객체를 생성하는 구체 팩토리 클래스
 * 문자열(viewType) 기반으로 어떤 화면(View) 객체를 생성할지 결정
 */
public class ViewFactory extends ViewAbstractFactory {
	private final InputReader reader;
	
	public ViewFactory(InputReader reader) {
	        this.reader = reader;
	    }

	/**
     * 문자열 기반 View 객체 생성 메서드
     * @param viewType 생성할 화면 타입 (예: "MAIN", "LOGIN")
     * @return View 객체, 없으면 null 반환
     */
    @Override
    public View createView(String viewType) {
        if (viewType == null) return null;

        switch (viewType.toUpperCase()) { // 문자열 대소문자 구분 없이 처리
            case "MAIN":
                return new MainView(reader);
            case "LOGIN":
                //return new LoginView(reader);  // LoginView도 View 인터페이스 구현 필요
            case "SIGNUP":
                //return new SignupView(reader); // SignupView도 View 인터페이스 구현 필요
            // 필요하면 다른 화면 추가 가능
            default:
                System.err.println("[ViewFactory] 알 수 없는 viewType: " + viewType);
                return null;
        }
    }

}
