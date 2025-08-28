package br.com.sghss.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

// Entidade que representa um paciente no sistema
@Entity
@Table(name = "pacientes")
@Getter
@Setter
@SQLDelete(sql = "UPDATE pacientes SET ativo = false WHERE id = ?")
@SQLRestriction("ativo = true")
public class Paciente {
    // Identificador único do paciente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // CPF do paciente
    private String cpf;
    
    // Data de nascimento do paciente
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    // Endereço do paciente
    private String endereco;

    // Associação com o usuário
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
    
    // Indica se o paciente está ativo
    private boolean ativo = true;
}