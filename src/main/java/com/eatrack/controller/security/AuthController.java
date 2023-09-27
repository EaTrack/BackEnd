package com.eatrack.controller.security;

import com.eatrack.model.records.DadosAuth;
import com.eatrack.model.records.UserRecord;
import com.eatrack.service.client.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginService loginService;

    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid DadosAuth dados) {
        return loginService.login(dados);
    }

    @PostMapping("/cadastrar-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addUser(@RequestBody UserRecord user) {
        return loginService.addUser(user);
    }

    @PostMapping("infos")
    public ResponseEntity<?> infos(HttpRequest request){
        return loginService.infos(request);
    }

}
