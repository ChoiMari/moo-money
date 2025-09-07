package io.github.choimari.moomoney.dto;
/**
 * 로그인 시도 시 이용
 */
public class LoginRequest {
	private final String id;
    private final String password;

    public LoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() { return id; }
    public String getPassword() { return password; }
}
