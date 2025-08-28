package br.com.sghss.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sghss.api.model.Paciente;
import br.com.sghss.api.service.PacienteService;

// Controller responsável pelos endpoints de pacientes
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;
    
    // Endpoint para cadastrar um novo paciente
    @PostMapping
    public Paciente cadastrar(@RequestBody Paciente novoPaciente) {
        return pacienteService.cadastrarPaciente(novoPaciente);
    }
    
    // Endpoint para listar todos os pacientes
    @GetMapping
    public List<Paciente> listarTodos() {
        return pacienteService.listarTodos();
    }
    
    // Endpoint para buscar paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Integer id) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        
        if (paciente.isPresent()) {
            return ResponseEntity.ok(paciente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint para atualizar dados de um paciente
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable Integer id, @RequestBody Paciente pacienteDetalhes) {
        try {
            Paciente pacienteAtualizado = pacienteService.atualizarPaciente(id, pacienteDetalhes);
            return ResponseEntity.ok(pacienteAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint para deletar um paciente (apenas ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            pacienteService.deletarPaciente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}