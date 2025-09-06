package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.PremiumMenuView;
import io.github.choimari.moomoney.views.ReportView;
import io.github.choimari.moomoney.views.View;

/**
 * 프리미엄 회원 전용 뷰를 생성하는 구체 팩토리 클래스
 */
public class PremiumViewFactory extends ViewAbstractFactory{

	protected PremiumViewFactory(InputReader reader) {
		super(reader);
	}

	@Override
	View createView(ViewType viewType) {
        switch(viewType) {
	    	case PREMIUM: return new PremiumMenuView(reader);
	        case REPORT: return new ReportView(reader);
	        default: return null;
        }
	}
}