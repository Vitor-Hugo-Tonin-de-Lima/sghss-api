package br.com.sghss.api.config;

import br.com.sghss.api.model.Usuario;
import br.com.sghss.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Classe responsável por inicializar dados no banco ao iniciar a aplicação
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método executado ao iniciar a aplicação
    @Override
    public void run(String... args) throws Exception {
    	// Verifica se o usuário admin existe, caso não exista, cria um novo
        if (usuarioRepository.findByEmail("admin@sghss.com") == null) {
            Usuario admin = new Usuario();
            admin.setNome("Admin SGHSS");
            admin.setEmail("admin@sghss.com");
            admin.setSenha(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            usuarioRepository.save(admin);
            System.out.println(">>> Usuário ADMIN de teste criado com sucesso!");
        }
    }
}