package br.com.sghss.api.repository;

import br.com.sghss.api.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
	boolean existsByProfissionalIdAndDataHora(Integer profissionalId, LocalDateTime dataHora);
}