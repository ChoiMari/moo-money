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
		System.out.printf(ConsoleStyle.apply("<🤗 %s>", ConsoleStyle.PASTEL_PINK),ViewType.SIGNUP.getType());
	}
	
	@Override
	protected void showContent() {
		 boolean success = false;
		 Map<String, String> signUpInfo;

		 // 유효성 통과할 때까지 반복
		 while (!success) {
			 System.out.println("\n");
		 	signUpInfo = new HashMap<>();
		 	signUpInfo.put("email", guestController.input("이메일 : "));
		 	signUpInfo.put("pw", guestController.input("비밀번호 : "));
		 	signUpInfo.put("ckpw", guestController.input("비밀번호 확인 : "));
		 	signUpInfo.put("nickname", guestController.input("닉네임 : "));
		 	signUpInfo.put("role", guestController.input("회원 등급 선택(1.일반 / 2.프리미엄) : "));
		 	System.out.println("===========================================\n");
		 	success = guestController.validationAndDuplicate(signUpInfo);

		 	if (!success) {
		     	System.out.println(ConsoleStyle.apply("다시 시도해주세요.\n", ConsoleStyle.ITALIC, ConsoleStyle.DARK_GREY));
		     	System.out.println("[아무 키] 계속 진행, [0] 취소(이전 단계로 이동)");
		     	String choice = guestController.input("입력 : ");
		     	 if ("0".equals(choice)) {
		                System.out.println(ConsoleStyle.apply("❌ 회원가입을 취소하고 이전 단계로 돌아갑니다.", ConsoleStyle.MEDIUM_GREY));
		                return; // showContent 종료 → 이전 메뉴로 복귀
		            } 
		 	}		 }
		System.out.println(ConsoleStyle.apply("회원가입 완료 🎉", ConsoleStyle.BOLD, ConsoleStyle.BLUE));
	}

	@Override
    protected void printFooter() {
        System.out.println();
    }

}
