package br.com.sghss.api.config;

import br.com.sghss.api.repository.UsuarioRepository;
import br.com.sghss.api.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Filtro de segurança responsável por validar o token JWT em cada requisição
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método chamado a cada requisição para validar o token e autenticar o usuário
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Recupera o token do cabeçalho Authorization
        var token = this.recoverToken(request);

        if (token != null) {
            // Valida o token e obtém o e-mail do usuário
            var subject = tokenService.validateToken(token);
            // Busca o usuário pelo e-mail
            UserDetails usuario = usuarioRepository.findByEmail(subject);
            // Cria o objeto de autenticação
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            // Define o usuário autenticado no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continua o processamento da requisição
        filterChain.doFilter(request, response);
    }

    // Método auxiliar para recuperar o token JWT do cabeçalho Authorization
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }
        
        return authHeader.replace("Bearer ", "");
    }
}