package io.github.choimari.moomoney.service;

import java.io.IOException;

import io.github.choimari.moomoney.domain.User;
import io.github.choimari.moomoney.dto.LoginRequest;
import io.github.choimari.moomoney.repository.IUserRepository;
import io.github.choimari.moomoney.repository.TXTUserRepository;

public class LoginService {
	private final TXTUserRepository tUserRepo;
	// private final IUserRepository userRepo;타입으로 받아도 됨

    public LoginService(TXTUserRepository tUserRepo) {
        this.tUserRepo = tUserRepo;
    }
    
    /**
     * 로그인 처리
     * @param dto 사용자가 입력한 이메일/비밀번호
     * @return User 객체 (로그인 성공) / null (로그인 실패)
     * @throws IOException 파일 읽기 오류
     */
    public User login(LoginRequest dto) throws IOException {
        //파일에서 이메일로 회원 조회
        User user = tUserRepo.findByEmail(dto.getEmail());
        if(user == null) return null; // 이메일 존재하지 않음

        //비밀번호 검증
        if(user.matchPw(dto.getPassword())) {
            return user; // 로그인 성공
        } else {
            return null; // 비밀번호 불일치
        }
    }
}
