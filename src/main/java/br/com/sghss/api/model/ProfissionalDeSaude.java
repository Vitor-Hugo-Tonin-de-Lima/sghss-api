package br.com.sghss.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

// Entidade que representa um profissional de saúde no sistema
@Entity
@Table(name = "profissionais_de_saude")
@Getter
@Setter
@SQLDelete(sql = "UPDATE profissionais_de_saude SET ativo = false WHERE id = ?")
@SQLRestriction("ativo = true")
public class ProfissionalDeSaude {

    // Identificador único do profissional
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Especialidade do profissional
    private String especialidade;

    // CRM do profissional
    private String crm;

    // Associação com o usuário
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
    
    // Indica se o profissional está ativo
    private boolean ativo = true;
}