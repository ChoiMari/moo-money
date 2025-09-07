package io.github.choimari.moomoney.repository;

import io.github.choimari.moomoney.domain.User;

public interface IUserRepository {
	 void save(User user); // 회원 저장
	 boolean existsByEmail(String email); // 이메일 중복 확인
	 
}
