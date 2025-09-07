package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.controller.BaseController;
import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.View;
/**
모든 뷰 *팩토리*가 상속받는 추상 팩토리 클래스
구체적인 뷰 팩토리(GuestViewFactory, RegularViewFactory 등)는 이 클래스를 상속받아
View 객체를 생성하는 createView() 메서드를 구현
	
ViewType(enum)을 기준으로 어떤 화면(View) 객체를 생성할지 결정하도록 강제
 */
public abstract class ViewAbstractFactory {
	
	 /**
     * 뷰 객체 생성 메서드
     * @param viewType 생성할 뷰 타입(enum)
     * @return View 객체, 정의되지 않은 타입이면 null 반환
     * 
     * - 실제 생성 로직은 구체 팩토리 클래스에서 구현
     * - 팩토리 패턴 적용: 객체 생성 책임을 팩토리로 분리
     */
    public abstract View createView(ViewType viewType, BaseController baseController);
}
