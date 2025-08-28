package br.com.sghss.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.sghss.api.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    UserDetails findByEmail(String email);
    
    @Query(value = "SELECT * FROM usuarios u WHERE u.email = :email LIMIT 1", nativeQuery = true)
    Optional<Usuario> findByEmailRegardlessOfStatus(String email);
}