package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.LogTraceView;
import io.github.choimari.moomoney.views.View;

/**
 * 시스템 관리자 전용 뷰를 생성하는 구체 팩토리 클래스
 */
public class SystemViewFactory extends ViewAbstractFactory{

	protected SystemViewFactory(InputReader reader) {
		super(reader);
	}

	@Override
	View createView(ViewType viewType) {
        switch(viewType) {
	    	case SYSTEM: return new LogTraceView(reader);
	        default: return null;
        }
	}

}
