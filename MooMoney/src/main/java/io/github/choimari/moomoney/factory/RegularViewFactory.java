package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.GuestMenuView;
import io.github.choimari.moomoney.views.LoginView;
import io.github.choimari.moomoney.views.MainView;
import io.github.choimari.moomoney.views.MyInfoView;
import io.github.choimari.moomoney.views.ReceiptDeleteView;
import io.github.choimari.moomoney.views.ReceiptRegisterView;
import io.github.choimari.moomoney.views.ReceiptSearchView;
import io.github.choimari.moomoney.views.ReceiptUpdateView;
import io.github.choimari.moomoney.views.RegularMenuView;
import io.github.choimari.moomoney.views.SignupView;
import io.github.choimari.moomoney.views.View;
/**
 * 일반 회원 전용 뷰를 생성하는 구체 팩토리 클래스
 */
public class RegularViewFactory extends ViewAbstractFactory{

	protected RegularViewFactory(InputReader reader) {
		super(reader);
	}

	@Override
	View createView(ViewType viewType) {
        switch(viewType) {
	    	case REGULAR: return new RegularMenuView(reader);
	        case RECEIPT_REGISTER: return new ReceiptRegisterView(reader);
	        case RECEIPT_SEARCH: return new ReceiptSearchView(reader);
	        case RECEIPT_UPDATE: return new ReceiptUpdateView(reader);
	        case RECEIPT_DELETE: return new ReceiptDeleteView(reader);
	        case MYINFO: return new MyInfoView(reader);
	        default: return null;
        }
	}
}
