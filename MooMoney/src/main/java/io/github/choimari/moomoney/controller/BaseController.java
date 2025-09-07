package io.github.choimari.moomoney.controller;

import io.github.choimari.moomoney.util.InputReader;

/**
 * 모든 권한별 컨트롤러가 상속받는 추상 클래스
 * run() 메서드 구현 강제
 */
public abstract class BaseController {
	protected final InputReader reader;
	
	public BaseController(InputReader reader) {
		this.reader = reader;
	}
	
	public abstract void run(); 
}
