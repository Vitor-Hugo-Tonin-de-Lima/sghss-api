package br.com.sghss.api.service;

import br.com.sghss.api.model.ProfissionalDeSaude;
import br.com.sghss.api.model.Usuario;
import br.com.sghss.api.repository.ProfissionalDeSaudeRepository;
import br.com.sghss.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

// Serviço responsável pela lógica de negócios relacionada aos profissionais de saúde
@Service
public class ProfissionalDeSaudeService {

    @Autowired
    private ProfissionalDeSaudeRepository profissionalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para cadastrar um novo profissional de saúde
    @Transactional
    public ProfissionalDeSaude cadastrarProfissional(ProfissionalDeSaude profissional) {
        String email = profissional.getUsuario().getEmail();

        // Verifica se já existe um usuário com o email informado
        Optional<Usuario> usuarioExistenteOpt = usuarioRepository.findByEmailRegardlessOfStatus(email);

        if (usuarioExistenteOpt.isPresent()) {
            Usuario usuarioExistente = usuarioExistenteOpt.get();

            // Se o usuário já está ativo, lança exceção
            if (usuarioExistente.isAtivo()) {
                throw new RuntimeException("Email já cadastrado para um usuário ativo.");
            } else {
                // Reativa usuário e profissional inativos
                usuarioExistente.setAtivo(true);
                usuarioExistente.setNome(profissional.getUsuario().getNome());
                usuarioExistente.setSenha(profissional.getUsuario().getSenha());
                usuarioExistente.setTelefone(profissional.getUsuario().getTelefone());

                ProfissionalDeSaude profissionalInativo = profissionalRepository.findByUsuario(usuarioExistente)
                        .orElseThrow(() -> new RuntimeException("Perfil profissional não encontrado para reativação."));

                profissionalInativo.setAtivo(true);
                profissionalInativo.setEspecialidade(profissional.getEspecialidade());
                profissionalInativo.setCrm(profissional.getCrm());

                return profissionalRepository.save(profissionalInativo);
            }
        }

        Usuario novoUsuario = profissional.getUsuario();

        // Criptografa a senha do novo usuário
        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);
        
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        profissional.setUsuario(usuarioSalvo);
        return profissionalRepository.save(profissional);
    }

    // Método para listar todos os profissionais de saúde
    public List<ProfissionalDeSaude> listarTodos() {
        return profissionalRepository.findAll();
    }

    // Método para buscar profissional de saúde por ID
    public Optional<ProfissionalDeSaude> buscarPorId(Integer id) {
        return profissionalRepository.findById(id);
    }

    // Método para atualizar os dados de um profissional de saúde
    @Transactional
    public ProfissionalDeSaude atualizarProfissional(Integer id, ProfissionalDeSaude profissionalDetalhes) {
        ProfissionalDeSaude profissionalExistente = profissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado com o id: " + id));

        Usuario usuarioExistente = profissionalExistente.getUsuario();
        Usuario usuarioDetalhes = profissionalDetalhes.getUsuario();

        usuarioExistente.setNome(usuarioDetalhes.getNome());
        usuarioExistente.setEmail(usuarioDetalhes.getEmail());
        usuarioExistente.setTelefone(usuarioDetalhes.getTelefone());

        profissionalExistente.setEspecialidade(profissionalDetalhes.getEspecialidade());
        profissionalExistente.setCrm(profissionalDetalhes.getCrm());

        return profissionalRepository.save(profissionalExistente);
    }

    // Método para deletar um profissional de saúde
    public void deletarProfissional(Integer id) {
    	
        if (!profissionalRepository.existsById(id)) {
            throw new RuntimeException("Profissional não encontrado com o id: " + id);
        }
        profissionalRepository.deleteById(id);
    }
}