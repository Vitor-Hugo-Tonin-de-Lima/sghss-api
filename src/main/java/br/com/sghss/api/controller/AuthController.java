package br.com.sghss.api.controller;

import br.com.sghss.api.model.Usuario;
import br.com.sghss.api.model.dto.AuthRequestDTO;
import br.com.sghss.api.model.dto.AuthResponseDTO;
import br.com.sghss.api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller responsável pela autenticação dos usuários
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    // Endpoint para login do usuário
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO dados) {
    	// Cria token de autenticação a partir dos dados recebidos
        var authToken = new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getSenha());

        // Realiza autenticação do usuário
        var authentication = authenticationManager.authenticate(authToken);

        // Recupera usuário autenticado e gera token JWT
        var usuario = (Usuario) authentication.getPrincipal();
        var token = tokenService.generateToken(usuario);

        // Retorna o token gerado para o cliente
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}