package br.com.cincoquatro.locacaoespaco.controller;

import br.com.cincoquatro.locacaoespaco.dto.PasswordChangerDto;
import br.com.cincoquatro.locacaoespaco.dto.UserProfileDto;
import br.com.cincoquatro.locacaoespaco.model.User;
import br.com.cincoquatro.locacaoespaco.service.CustomUserDetailsService;
import br.com.cincoquatro.locacaoespaco.service.UserService;
import br.com.cincoquatro.locacaoespaco.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

    @PutMapping("/profile")
    public ResponseEntity<?> changeProfile(@RequestBody @Valid UserProfileDto userProfileDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changeProfile(userProfileDto, user);

        return ResponseUtil.result_200("Perfil alterado com sucesso!");
    }

    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordChangerDto passwordChanger) {
        jwtUserDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
        return ResponseUtil.result_200("Senha alterada com sucesso!");
    }

}
