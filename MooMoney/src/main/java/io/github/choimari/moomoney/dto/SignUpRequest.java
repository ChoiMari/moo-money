package io.github.choimari.moomoney.dto;

import io.github.choimari.moomoney.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원가입 데이터를 DTO로
 */
@Getter @AllArgsConstructor
public class SignUpRequest {
	private String email;
	private String password;
	private Role role;
	private String nickname;
	
}
