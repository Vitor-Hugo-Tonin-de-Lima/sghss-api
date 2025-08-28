package br.com.sghss.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@SQLDelete(sql = "UPDATE usuarios SET ativo = false WHERE id = ?")
@SQLRestriction("ativo = true")
public class Usuario implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;
    
    private String telefone;
    private String role;
    private boolean ativo = true;

    // --- MÉTODOS DA INTERFACE UserDetails IGNORADOS NO JSON ---

    @Override
    @JsonIgnore // Ignora este método na serialização
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.toUpperCase()));
    }

    @Override
    @JsonIgnore // Ignora este método na serialização (já temos o @JsonProperty em 'senha')
    public String getPassword() {
        return this.senha;
    }

    @Override
    @JsonIgnore // Ignora este método na serialização (o campo 'email' já é o nosso username)
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.ativo;
    }
}