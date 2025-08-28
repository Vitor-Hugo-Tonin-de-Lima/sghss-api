package br.com.sghss.api.service;

import br.com.sghss.api.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

// Serviço responsável pela geração e validação de tokens JWT
@Service
public class TokenService {

    // Segredo utilizado para assinar o token
    @Value("${api.security.token.secret}")
    private String secret;

    private Key key;

    // Inicializa a chave de assinatura do token ao iniciar o bean
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Gera um token JWT para o usuário informado
    public String generateToken(Usuario usuario) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (2 * 60 * 60 * 1000));

        return Jwts.builder()
                .setIssuer("SGHSS API")
                .setSubject(usuario.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Valida o token JWT e retorna o e-mail do usuário (subject)
    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return "";
        }
    }
}