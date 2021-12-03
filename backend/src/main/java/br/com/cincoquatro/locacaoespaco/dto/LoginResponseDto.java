package br.com.cincoquatro.locacaoespaco.dto;

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
    private String role;

    public LoginResponseDto(String token, Long id, String username, String firstname, String lastname, String systemName, String role) {
        super();
        this.token = token;
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.systemName = systemName;
        this.role = role;
    }

}
