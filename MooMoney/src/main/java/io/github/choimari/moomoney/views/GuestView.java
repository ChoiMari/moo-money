package io.github.choimari.moomoney.views;

import io.github.choimari.moomoney.util.InputReader;

public class GuestView implements View{
	private final InputReader reader;
	public GuestView(InputReader reader) {
		this.reader = reader;
	}
	@Override
	public void show() {
	    System.out.println("[1] 로그인 [2] 회원가입");
	    System.out.println("원하시는 메뉴를 선택해주세요.");
	    reader.readLine("입력 : ");
	}

}
