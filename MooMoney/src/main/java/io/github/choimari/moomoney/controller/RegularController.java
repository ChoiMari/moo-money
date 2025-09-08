package io.github.choimari.moomoney.controller;

import io.github.choimari.moomoney.App;
import io.github.choimari.moomoney.domain.User;
import io.github.choimari.moomoney.factory.ViewAbstractFactory;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.util.ConsoleStyle;
import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.RegularMenuView;
import io.github.choimari.moomoney.views.View;

public class RegularController extends BaseController {
	 private final App app;
	 private final ViewAbstractFactory regularViewFactory;
	 
	    public RegularController(InputReader reader, App app, ViewAbstractFactory regularViewFactory) {
	        super(reader);
	        this.app = app;
	        this.regularViewFactory = regularViewFactory;
	    }

	@Override
	public void run() {
		boolean running = true;
        View menuView = regularViewFactory.createView(ViewType.REGULAR, this);

        while (running) {
            menuView.show();  // 화면 출력
            String choice = input("입력 : ");
            switch (choice) {
                case "1":
                    // 영수증 등록 로직
                    break;
                case "2":
                    // 영수증 조회 로직
                    break;
                case "3":
                	System.out.println(ConsoleStyle.apply("\n로그아웃 하였습니다.🖐🏻", ConsoleStyle.PURPLE));
                    app.setCurrentUser(null); // 로그아웃
                    running = false;
                    break;
                default:
                    System.out.println("[입력 오류] 다시 입력해주세요.");
            }
        }
	}

	/**
	 * View에서 호출해서 리턴받는 용도
	 * @return 사용자 입력값
	 */
	public String input(String prompt) {
		return reader.readLine(prompt);
	}
}
