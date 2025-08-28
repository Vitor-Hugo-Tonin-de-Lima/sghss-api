package br.com.sghss.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.sghss.api.model.Paciente;
import br.com.sghss.api.model.Usuario;
import br.com.sghss.api.repository.PacienteRepository;
import br.com.sghss.api.repository.UsuarioRepository;

// Serviço responsável pela lógica de negócios relacionada aos pacientes
@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para cadastrar um novo paciente
    @Transactional
    public Paciente cadastrarPaciente(Paciente paciente) {
        String email = paciente.getUsuario().getEmail();

        // Verifica se já existe um usuário com o email informado
        Optional<Usuario> usuarioExistenteOpt = usuarioRepository.findByEmailRegardlessOfStatus(email);

        if (usuarioExistenteOpt.isPresent()) {
            Usuario usuarioExistente = usuarioExistenteOpt.get();

            // Se o usuário já está ativo, lança exceção
            if (usuarioExistente.isAtivo()) {
                throw new RuntimeException("Email já cadastrado para um usuário ativo.");
            } else {
                // Reativa usuário e paciente inativos
                usuarioExistente.setAtivo(true);
                usuarioExistente.setNome(paciente.getUsuario().getNome());
                
                Paciente pacienteInativo = pacienteRepository.findByUsuarioIdRegardlessOfStatus(usuarioExistente.getId())
                        .orElseThrow(() -> new RuntimeException("Perfil de paciente inativo não encontrado para o usuário."));

                pacienteInativo.setAtivo(true);
                
                return pacienteRepository.save(pacienteInativo);
            }
        }

        Usuario novoUsuario = paciente.getUsuario();

        // Criptografa a senha do novo usuário
        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        paciente.setUsuario(usuarioSalvo);
        return pacienteRepository.save(paciente);
    }
    
    // Método para listar todos os pacientes
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }
    
    // Método para buscar paciente por ID
    public Optional<Paciente> buscarPorId(Integer id) {
        return pacienteRepository.findById(id);
    }
    
    // Método para atualizar os dados de um paciente
    @Transactional
    public Paciente atualizarPaciente(Integer id, Paciente pacienteDetalhes) {
    	
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com o id: " + id));

        Usuario usuarioExistente = pacienteExistente.getUsuario();
        usuarioExistente.setNome(pacienteDetalhes.getUsuario().getNome());
        usuarioExistente.setEmail(pacienteDetalhes.getUsuario().getEmail());

        pacienteExistente.setCpf(pacienteDetalhes.getCpf());
        pacienteExistente.setDataNascimento(pacienteDetalhes.getDataNascimento());
        pacienteExistente.setEndereco(pacienteDetalhes.getEndereco());

        return pacienteRepository.save(pacienteExistente);
    }
    
    // Método para deletar um paciente
    public void deletarPaciente(Integer id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente não encontrado com o id: " + id);
        }
        pacienteRepository.deleteById(id);
    }
}