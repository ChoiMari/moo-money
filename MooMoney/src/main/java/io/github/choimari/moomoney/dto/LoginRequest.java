package io.github.choimari.moomoney.dto;
/**
 * 로그인 시도 시 이용
 */
public class LoginRequest {
	private final String email;
    private final String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
