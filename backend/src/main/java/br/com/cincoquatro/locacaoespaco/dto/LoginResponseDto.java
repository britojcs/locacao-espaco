package br.com.cincoquatro.locacaoespaco.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {

	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String firstname;
	private String lastname;
	private String systemName;
	private List<String> roles;

	public LoginResponseDto(String token, Long id, String username, String firstname, String lastname, String systemName, List<String> roles) {
		super();
		this.token = token;
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.systemName = systemName;
		this.roles = roles;
	}

}
