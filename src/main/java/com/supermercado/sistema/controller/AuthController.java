package com.supermercado.sistema.controller;

import com.supermercado.sistema.model.Usuario;
import com.supermercado.sistema.repository.UsuarioRepository;
import com.supermercado.sistema.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*") // permite chamadas do frontend
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public String login(@RequestBody Usuario userLogin) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(userLogin.getEmail());

        if (usuario.isPresent() && usuario.get().getSenha().equals(userLogin.getSenha())) {
            return JwtUtil.generateToken(userLogin.getEmail());
        } else {
            throw new RuntimeException("E-mail ou senha inválidos");
        }
    }
}
