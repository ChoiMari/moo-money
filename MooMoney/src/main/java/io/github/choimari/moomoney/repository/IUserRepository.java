package io.github.choimari.moomoney.repository;

import java.io.IOException;

import io.github.choimari.moomoney.domain.User;
import io.github.choimari.moomoney.dto.SignUpRequest;

public interface IUserRepository {
	 void save(User user) throws IOException; // 회원 저장
//	 boolean existsByEmail(String email); // 이메일 중복 확인
//	 boolean existsByNickname(String nickname); // 이메일 중복 확인
//	 SignUpRequest findByEmail(String email);
//	 SignUpRequest findByNickname(String nickname);	 
}
