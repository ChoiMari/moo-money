package io.github.choimari.moomoney.controller;


import io.github.choimari.moomoney.factory.ControllerFactory;
import io.github.choimari.moomoney.factory.ControllerType;
import io.github.choimari.moomoney.factory.GuestViewFactory;
import io.github.choimari.moomoney.factory.RoleControllerFactory;
import io.github.choimari.moomoney.factory.ViewAbstractFactory;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.View;

public class MainController extends BaseController{
	// 주입 받음
    private final ControllerFactory controllerFactory;
    private final ViewAbstractFactory guestFactory;
    
    
    public MainController(InputReader reader, ControllerFactory controllerFactory, ViewAbstractFactory guestFactory) {
		super(reader);
		this.controllerFactory = controllerFactory;
		this.guestFactory = guestFactory;
    }

	public void run() {
        View mainView = guestFactory.createView(ViewType.MAIN, this); // 팩토리로 메인 뷰 객체 생성
        BaseController guestController = controllerFactory.createController(reader, ControllerType.GUEST, guestFactory); //팩토리로 컨트롤러 객체 생성
        boolean running = true; // 반복 제어
        
        while(running) {
        	mainView.show(); // 화면에 출력
        	String choice = input();
        	switch(choice) {
        	case "1" : 
	            guestController.run(); 
	            break;
        	case "2" :
        		System.out.println("===============================");
        		System.out.println("다음에 다시 만나요 🐈‍⬛");
        		running = false;
        		break;
        	default : 
        		System.out.println("===============================");
        		System.out.println("[입력오류] 다시 입력해주세요.\n");
        	} 	
        } 
    }
	
	public String input() {
		return reader.readLine("입력 : ");
	}
	
}
