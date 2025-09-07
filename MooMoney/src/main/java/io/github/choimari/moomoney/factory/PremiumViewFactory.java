package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.controller.BaseController;
import io.github.choimari.moomoney.views.PremiumMenuView;
import io.github.choimari.moomoney.views.ReportView;
import io.github.choimari.moomoney.views.View;

/**
 * 프리미엄 회원 전용 뷰를 생성하는 구체 팩토리 클래스
 */
public class PremiumViewFactory extends ViewAbstractFactory{
	@Override
	public View createView(ViewType viewType, BaseController baseController) {
        switch(viewType) {
	    	case PREMIUM: return new PremiumMenuView();
	        case REPORT: return new ReportView();
	        default: return null;
        }
	}
}