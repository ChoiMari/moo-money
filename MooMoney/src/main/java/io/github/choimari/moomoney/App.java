package io.github.choimari.moomoney;

import io.github.choimari.moomoney.controller.BaseController;
import io.github.choimari.moomoney.controller.MainController;
import io.github.choimari.moomoney.factory.ControllerFactory;
import io.github.choimari.moomoney.factory.GuestViewFactory;
import io.github.choimari.moomoney.factory.RoleControllerFactory;
import io.github.choimari.moomoney.factory.ViewAbstractFactory;
import io.github.choimari.moomoney.util.InputReader;


/**
 * 프로그램 진입점
 */
public class App { 
	public static void main(String[] args) {
		InputReader reader = InputReader.getInstance(); 
        // 팩토리 객체 생성
        ControllerFactory controllerFactory = new RoleControllerFactory();
        ViewAbstractFactory guestFactory = new GuestViewFactory();

        // 생성자 주입
        BaseController main = new MainController(reader, controllerFactory, guestFactory);
        main.run();
	}
}
