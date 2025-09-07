package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.controller.BaseController;
import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.ExpenseSummaryView;
import io.github.choimari.moomoney.views.View;

/**
 * 관리자 전용 뷰를 생성하는 구체 팩토리 클래스
 */
public class AdminViewFactory extends ViewAbstractFactory{

	@Override
	public View createView(ViewType viewType, BaseController baseController) {
        switch(viewType) {
	    	case EXPENSE_SUMMARY: return new ExpenseSummaryView();
	        default: return null;
        }
	}
}