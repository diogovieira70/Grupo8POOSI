package ProjetoPoo;

import java.time.LocalDateTime;

/**
 * Classe que representa um exame médico.
 */
public class Exame {
    private LocalDateTime dataHora;
    private String designacao;
    private String observacoes;
    private Medico medico;
    private Paciente paciente;

    /**
     * Construtor da classe Exame.
     *
     * @param dataHora    A data e hora do exame.
     * @param designacao  A designação do exame.
     * @param medico      O médico responsável pelo exame.
     * @param paciente    O paciente que realizará o exame.
     */
    public Exame(LocalDateTime dataHora, String designacao, Medico medico, Paciente paciente) {
        this.dataHora = dataHora;
        this.designacao = designacao;
        this.medico = medico;
        this.paciente = paciente;
    }

    /**
     * Obtém a data e hora do exame.
     *
     * @return A data e hora do exame.
     */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Obtém a designação do exame.
     *
     * @return A designação do exame.
     */
    public String getDesignacao() {
        return designacao;
    }

    /**
     * Obtém o médico responsável pelo exame.
     *
     * @return O médico responsável pelo exame.
     */
    public Medico getMedico() {
        return medico;
    }

    /**
     * Obtém o paciente que realizará o exame.
     *
     * @return O paciente que realizará o exame.
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * Obtém as observações do exame.
     *
     * @return As observações do exame.
     */
    public String getObservacoes() {
        return observacoes;
    }

    /**
     * Define as observações do exame.
     *
     * @param observacoes As observações do exame.
     * @throws IllegalArgumentException Se as observações excederem 30 caracteres.
     */
    public void setObservacoes(String observacoes) {
        if (observacoes.length() > 30) {
            throw new IllegalArgumentException("Observações não podem exceder 30 caracteres.");
        }
        this.observacoes = observacoes;
    }

    /**
     * Retorna uma representação em string do exame.
     *
     * @return Uma string representando o exame.
     */
    @Override
    public String toString() {
        return "Exame{" +
                "dataHora=" + dataHora +
                ", designacao='" + designacao + '\'' +
                ", observacoes='" + observacoes + '\'' +
                ", medico=" + medico.getNome() +
                ", paciente=" + paciente.getNome() +
                '}';
    }
}
