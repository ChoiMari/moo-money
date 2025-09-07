package io.github.choimari.moomoney.controller;

import io.github.choimari.moomoney.factory.GuestViewFactory;
import io.github.choimari.moomoney.factory.ViewAbstractFactory;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.View;

public class GuestController extends BaseController{

	public GuestController(InputReader reader) {
		super(reader);
	}

	@Override
	public void run() {
		ViewAbstractFactory guestFactory = new GuestViewFactory();
        View guestView = guestFactory.createView(ViewType.GUEST, this);
        
        
        boolean running = true;
        while(running) {
        	guestView.show(); // 비 로그인 시 메뉴화면 출력
        	switch(reader.readLine("입력 : ")) {
	        	case "1" : guestFactory.createView(ViewType.LOGIN, this).show();
	        		//running = false;
	        		break;
	        	case "2" : guestFactory.createView(ViewType.SIGNUP, this).show();
	        		break;
	        	case "3" : return;
	        	default : System.out.println("[입력 오류] : 다시 입력해 주세요.");
        	}
        }  
	}
	
	public String input() {
		return reader.readLine("입력 : ");
	}
		
}
