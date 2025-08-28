package br.com.sghss.api.model.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ConsultaAgendamentoDTO {
    private Integer pacienteId;
    private Integer profissionalId;
    private LocalDateTime dataHora;
}