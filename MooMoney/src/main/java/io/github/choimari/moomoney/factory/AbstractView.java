package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.util.InputReader;
import io.github.choimari.moomoney.views.View;

/**
 * 모든 콘솔 뷰가 상속받는 추상 클래스
 * 
 * 템플릿 메서드 패턴 적용: show()는 공통 구조(헤더 → 내용 → 푸터) 제공
 * 각 뷰는 showContent()만 구현하면 됨
 * 헤더랑 푸터가 맘에 안들면 protected로 바꿔서 각 뷰에서 따로 오버라이드 하게 해도 된다.
 */
public abstract class AbstractView implements View {

	/**
	 *  템플릿 메서드
	 * - show() 호출 시 전체 화면 구조를 정의
     * - final로 선언(오버라이드 금지) → 하위 클래스에서 show() 오버라이드 못함
     *   (헤더 + showContent() + 푸터 흐름 보장)
	 */
    @Override
    public final void show() {
        printHeader(); // 공통 헤더 출력
        showContent(); // 구체 뷰에서 구현
        printFooter(); // 공통 푸터 출력
    }

    /**
     * 화면 상단 헤더 출력
     * 필요시 protected로 바꾸면 서브 클래스에서 재정의 가능
     */
    protected void printHeader() {
        System.out.println("============================================");
    }

    /**
     * 구체 뷰에서 반드시 구현해야 하는 화면 내용(강제성 부여)
     */
    protected abstract void showContent();

    /**
     * 화면 하단 푸터 출력
     * 필요시 protected로 바꾸면 서브 클래스에서 재정의 가능
     */
    protected void printFooter() {
        System.out.println("============================================");
    }
}


