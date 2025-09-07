package io.github.choimari.moomoney.views;

import java.awt.MultipleGradientPaint.ColorSpaceType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.choimari.moomoney.controller.GuestController;
import io.github.choimari.moomoney.dto.SignUpRequest;
import io.github.choimari.moomoney.factory.AbstractView;
import io.github.choimari.moomoney.factory.ViewType;
import io.github.choimari.moomoney.util.ConsoleStyle;


public class SignupView extends AbstractView{
	private final GuestController guestController;
	
	public SignupView(GuestController guestController) {
		this.guestController = guestController;
	}

	@Override
	protected void printHeader() {
		System.out.println("============================================");
		System.out.printf("\n<%s>\n\n",ViewType.SIGNUP.getType());
	}
	
	@Override
	protected void showContent() {
		Map<String, String> singUpInfoList = new HashMap<>(); // key : 입력구분, value : 사용자 입력값
		singUpInfoList.put("email", guestController.input("이메일 : "));
		singUpInfoList.put("pw", guestController.input("비밀번호 : "));
		singUpInfoList.put("ckpw", guestController.input("비밀번호 확인 : "));
		singUpInfoList.put("nickname", guestController.input("닉네임 : "));
		singUpInfoList.put("role", guestController.input("회원 등급 선택(1.일반 / 2.프리미엄) : "));
		guestController.validation(singUpInfoList);
		//SignUpRequest signup = new SignUpRequest(email, role, null, nickname);
		// signup컨트롤러 호출
	}

	@Override
    protected void printFooter() {
		System.out.println(ConsoleStyle.apply("\n회원가입 완료", ConsoleStyle.BOLD, ConsoleStyle.BLUE) + "🎉\n");
        System.out.println();
    }

}
