package io.github.choimari.moomoney.views;

import io.github.choimari.moomoney.controller.GuestController;
import io.github.choimari.moomoney.factory.AbstractView;
import io.github.choimari.moomoney.factory.ViewType;

public class GuestMenuView extends AbstractView{
	private GuestController guestController;
	
	public GuestMenuView(GuestController guestController) {
		this.guestController = guestController;
	}
	
	
	@Override
    protected void printHeader() {
		System.out.println("=============================================");
        System.out.printf("<%s 메뉴>\n\n",ViewType.GUEST.getType());
    }

	@Override
    protected void printFooter() {
        System.out.println("=============================================");
    }

	@Override
	protected void showContent() {
		System.out.println("[1] 로그인\n[2] 회원가입\n[3] 이전 화면");
	}

}
