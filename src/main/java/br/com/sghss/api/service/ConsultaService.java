package br.com.sghss.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sghss.api.model.Consulta;
import br.com.sghss.api.model.Paciente;
import br.com.sghss.api.model.ProfissionalDeSaude;
import br.com.sghss.api.model.dto.ConsultaAgendamentoDTO;
import br.com.sghss.api.repository.ConsultaRepository;
import br.com.sghss.api.repository.PacienteRepository;
import br.com.sghss.api.repository.ProfissionalDeSaudeRepository;

// Serviço responsável pela lógica de negócios relacionada às consultas médicas
@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalDeSaudeRepository profissionalRepository;

    // Método para agendar uma nova consulta
    @Transactional
    public Consulta agendarConsulta(ConsultaAgendamentoDTO dados) {
    	
    	 if (consultaRepository.existsByProfissionalIdAndDataHora(dados.getProfissionalId(), dados.getDataHora())) {
    	        throw new RuntimeException("Profissional já possui uma consulta agendada para este horário.");
    	    }
    	 
        // 1. Busca o paciente e o profissional no banco de dados pelos IDs fornecidos.
        Paciente paciente = pacienteRepository.findById(dados.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado!"));

        ProfissionalDeSaude profissional = profissionalRepository.findById(dados.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado!"));

        // 2. Cria uma nova instância da entidade Consulta.
        Consulta novaConsulta = new Consulta();
        novaConsulta.setPaciente(paciente);
        novaConsulta.setProfissional(profissional);
        novaConsulta.setDataHora(dados.getDataHora());
        novaConsulta.setStatus("AGENDADA");

        // 3. Salva a nova consulta no banco de dados.
        return consultaRepository.save(novaConsulta);
    }
    
    // Método para listar todas as consultas
    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }
   
    // Método para cancelar uma consulta existente
    @Transactional
    public void cancelarConsulta(Integer id) {
        if (!consultaRepository.existsById(id)) {
            throw new RuntimeException("Consulta não encontrada!");
        }

        consultaRepository.deleteById(id);
    }
}