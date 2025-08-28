package br.com.sghss.api.controller;

import br.com.sghss.api.model.ProfissionalDeSaude;
import br.com.sghss.api.service.ProfissionalDeSaudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Controller responsável pelos endpoints de profissionais de saúde
@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalDeSaudeController {

    @Autowired
    private ProfissionalDeSaudeService profissionalService;

    // Endpoint para cadastrar um novo profissional de saúde
    @PostMapping
    public ProfissionalDeSaude cadastrar(@RequestBody ProfissionalDeSaude novoProfissional) {
        return profissionalService.cadastrarProfissional(novoProfissional);
    }

    // Endpoint para listar todos os profissionais de saúde
    @GetMapping
    public List<ProfissionalDeSaude> listarTodos() {
        return profissionalService.listarTodos();
    }

    // Endpoint para buscar profissional de saúde por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalDeSaude> buscarPorId(@PathVariable Integer id) {
        Optional<ProfissionalDeSaude> profissional = profissionalService.buscarPorId(id);
        return profissional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para atualizar dados de um profissional de saúde
    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalDeSaude> atualizar(@PathVariable Integer id, @RequestBody ProfissionalDeSaude profissionalDetalhes) {
        try {
            ProfissionalDeSaude atualizado = profissionalService.atualizarProfissional(id, profissionalDetalhes);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para deletar um profissional de saúde
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            profissionalService.deletarProfissional(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}