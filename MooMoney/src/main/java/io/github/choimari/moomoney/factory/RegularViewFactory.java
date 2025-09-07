package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.controller.BaseController;
import io.github.choimari.moomoney.views.MyInfoView;
import io.github.choimari.moomoney.views.ReceiptDeleteView;
import io.github.choimari.moomoney.views.ReceiptRegisterView;
import io.github.choimari.moomoney.views.ReceiptSearchView;
import io.github.choimari.moomoney.views.ReceiptUpdateView;
import io.github.choimari.moomoney.views.RegularMenuView;
import io.github.choimari.moomoney.views.View;
/**
 * 일반 회원 전용 뷰를 생성하는 구체 팩토리 클래스
 */
public class RegularViewFactory extends ViewAbstractFactory{

	@Override
	public View createView(ViewType viewType, BaseController baseController) {
        switch(viewType) {
	    	case REGULAR: return new RegularMenuView();
	        case RECEIPT_REGISTER: return new ReceiptRegisterView();
	        case RECEIPT_SEARCH: return new ReceiptSearchView();
	        case RECEIPT_UPDATE: return new ReceiptUpdateView();
	        case RECEIPT_DELETE: return new ReceiptDeleteView();
	        case MYINFO: return new MyInfoView();
	        default: return null;
        }
	}
}
