package br.com.sghss.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

// Entidade que representa uma consulta médica no sistema
@Entity
@Table(name = "consultas")
@Getter
@Setter
@SQLDelete(sql = "UPDATE consultas SET ativo = false WHERE id = ?")
@SQLRestriction("ativo = true")
public class Consulta {

    // Identificador único da consulta
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Data e hora da consulta
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    // Status da consulta (ex: AGENDADA, CANCELADA)
    private String status;

    // Indica se a consulta está ativa
    private boolean ativo = true;

    // Associação com o paciente
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    // Associação com o profissional de saúde
    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalDeSaude profissional;
}