package io.github.choimari.moomoney;

import io.github.choimari.moomoney.factory.ViewFactory;
import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.View;

/**
 * 프로그램 진입점, Scanner 입력 처리, 메뉴 루프 담당 클래스
 */
public class App { 
	public static void main(String[] args) {
		InputReader reader = InputReader.getInstance();
		ViewFactory factory = new ViewFactory(reader);
		View main = factory.createView("main");
		main.show();
	}
}
