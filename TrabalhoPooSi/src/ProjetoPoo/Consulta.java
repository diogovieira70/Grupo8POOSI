package ProjetoPoo;

import java.time.LocalDateTime;

/**
 * Classe que representa uma consulta médica.
 */
public class Consulta {
    private LocalDateTime dataHora;
    private Medico medico;
    private Paciente paciente;

    /**
     * Construtor da classe Consulta.
     *
     * @param dataHora A data e hora da consulta.
     * @param medico O médico responsável pela consulta.
     * @param paciente O paciente que será atendido na consulta.
     */
    public Consulta(LocalDateTime dataHora, Medico medico, Paciente paciente) {
        this.dataHora = dataHora;
        this.medico = medico;
        this.paciente = paciente;
    }

    /**
     * Obtém a data e hora da consulta.
     *
     * @return A data e hora da consulta.
     */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Obtém o médico responsável pela consulta.
     *
     * @return O médico responsável pela consulta.
     */
    public Medico getMedico() {
        return medico;
    }

    /**
     * Obtém o paciente que será atendido na consulta.
     *
     * @return O paciente que será atendido na consulta.
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * Retorna uma representação em string da consulta.
     *
     * @return Uma string representando a consulta.
     */
    @Override
    public String toString() {
        return "Consulta{" +
                "dataHora=" + dataHora +
                ", medico=" + medico.getNome() +
                ", paciente=" + paciente.getNome() +
                '}';
    }
}

