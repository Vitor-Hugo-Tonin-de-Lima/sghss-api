package br.com.sghss.api.repository;

import br.com.sghss.api.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    @Query(value = "SELECT * FROM pacientes p WHERE p.usuario_id = :usuarioId LIMIT 1", nativeQuery = true)
    Optional<Paciente> findByUsuarioIdRegardlessOfStatus(@Param("usuarioId") Integer usuarioId);
}