package br.com.sghss.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sghss.api.model.ProfissionalDeSaude;
import br.com.sghss.api.model.Usuario;

@Repository
public interface ProfissionalDeSaudeRepository extends JpaRepository<ProfissionalDeSaude, Integer> {
    Optional<ProfissionalDeSaude> findByUsuario(Usuario usuario);
}