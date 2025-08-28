package br.com.sghss.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller responsável por informar o status da API
@RestController
@RequestMapping("/api/status")
public class StatusController {

    // Endpoint para verificar se a API está online
    @GetMapping
    public String verificarStatus() {
        return "API do SGHSS está no ar!";
    }
}