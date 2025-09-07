package io.github.choimari.moomoney.factory;

import io.github.choimari.moomoney.controller.BaseController;
import io.github.choimari.moomoney.util.InputReader;
/**
 * 컨트롤러 객체 생성 메서드
 * @param type 권한 타입
 * @return BaseController 하위 객체
 */
public interface ControllerFactory {
	 BaseController createController(InputReader reader, ControllerType type, ViewAbstractFactory factory);
}
