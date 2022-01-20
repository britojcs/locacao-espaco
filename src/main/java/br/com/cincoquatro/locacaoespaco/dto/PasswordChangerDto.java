package br.com.cincoquatro.locacaoespaco.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PasswordChangerDto {

    @NotEmpty
    public String oldPassword;
    
    @NotEmpty
    @Size(min = 5, message = "A senha deve conter pelo menos 5 caracteres")
    public String newPassword;

}
