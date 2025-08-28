package br.com.sghss.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sghss.api.model.Consulta;
import br.com.sghss.api.model.dto.ConsultaAgendamentoDTO;
import br.com.sghss.api.service.ConsultaService;

// Controller responsável pelos endpoints de consultas médicas
@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    // Endpoint para agendar uma nova consulta
    @PostMapping
    public ResponseEntity<Consulta> agendar(@RequestBody ConsultaAgendamentoDTO dados) {
        Consulta novaConsulta = consultaService.agendarConsulta(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConsulta);
    }
    
    // Endpoint para listar todas as consultas
    @GetMapping
    public ResponseEntity<List<Consulta>> listarTodas() {
        List<Consulta> consultas = consultaService.listarTodas();
        return ResponseEntity.ok(consultas);
    }
    
    // Endpoint para cancelar uma consulta pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Integer id) {
        try {
            consultaService.cancelarConsulta(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}