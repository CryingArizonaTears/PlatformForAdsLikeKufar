package com.senla.controllers;

import com.senla.api.service.IUserService;
import com.senla.model.UserLogin;
import com.senla.model.dto.UserDto;
import com.senla.security.filter.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private IUserService userService;
    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@RequestBody UserDto userDto) {
        userService.registration(userDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authorization(@RequestBody UserDto userDto) {
        UserLogin userLogin = userService.getByUsernameAndPassword(userDto);
        String token = tokenProvider.createToken(userLogin.getUsername());
        return ResponseEntity.ok(token);
    }
}