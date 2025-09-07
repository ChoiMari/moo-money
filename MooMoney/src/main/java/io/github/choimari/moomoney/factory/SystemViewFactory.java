package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.controller.BaseController;
import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.LogTraceView;
import io.github.choimari.moomoney.views.View;

/**
 * 시스템 관리자 전용 뷰를 생성하는 구체 팩토리 클래스
 */
public class SystemViewFactory extends ViewAbstractFactory{

	@Override
	public View createView(ViewType viewType, BaseController baseController) {
        switch(viewType) {
	    	case SYSTEM: return new LogTraceView();
	        default: return null;
        }
	}

}
