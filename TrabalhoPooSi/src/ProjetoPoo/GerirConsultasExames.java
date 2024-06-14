package ProjetoPoo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe responsável por gerir consultas e exames médicos.
 */
public class GerirConsultasExames {
    private List<Exame> exames;
    private List<Consulta> consultas;
    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private Map<Medico, List<LocalDateTime>> horariosDisponiveis;

    private static GerirConsultasExames gestor;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /**
     * Construtor da classe GerirConsultasExames.
     */
    public GerirConsultasExames() {
        this.exames = new ArrayList<>();
        this.consultas = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.horariosDisponiveis = new HashMap<>();
    }

    /**
     * Inicializa o gestor de consultas e exames.
     */
    public static void inicializarGestor() {
        if (gestor == null) {
            gestor = new GerirConsultasExames();
        }
    }

    /**
     * Obtém a instância do gestor de consultas e exames.
     * 
     * @return A instância do gestor.
     */
    public static GerirConsultasExames getInstance() {
        return gestor;
    }

    /**
     * Regista um médico.
     * 
     * @param medico O médico a ser registado.
     */
    public void registarMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("Médico não pode ser nulo.");
        }
        if (medicos.contains(medico)) {
            throw new IllegalArgumentException("Médico já registado.");
        }
        this.medicos.add(medico);
        inicializarHorariosDisponiveis(medico);
    }

    /**
     * Inicializa os horários disponíveis para um médico.
     * 
     * @param medico O médico.
     */
    private void inicializarHorariosDisponiveis(Medico medico) {
        List<LocalDateTime> horarios = new ArrayList<>();
        // Supondo que há horários disponíveis entre 8h e 17h de segunda a sexta
        for (int dia = 1; dia <= 30; dia++) {
            for (int hora = 8; hora < 17; hora++) { // Corrigido para 17h, não 26h
                horarios.add(LocalDateTime.of(2024, 6, dia, hora, 0));
            }
        }
        horariosDisponiveis.put(medico, horarios);
    }

    /**
     * Regista um paciente.
     * 
     * @param paciente O paciente a ser registado.
     */
    public void registarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente não pode ser nulo.");
        }
        if (!paciente.getId().matches("\\d+")) {
            throw new IllegalArgumentException("ID do paciente deve conter apenas números.");
        }
        for (Paciente p : pacientes) {
            if (p.getId().equals(paciente.getId())) {
                throw new IllegalArgumentException("Paciente com este ID já registado.");
            }
        }
        this.pacientes.add(paciente);
    }

    /**
     * Agenda uma consulta para um paciente.
     * 
     * @param paciente O paciente.
     * @param dataHora A data e hora da consulta.
     * @param medico   O médico responsável.
     */
    public void agendarConsulta(Paciente paciente, LocalDateTime dataHora, Medico medico) {
        if (paciente == null || dataHora == null || medico == null) {
            throw new IllegalArgumentException("Dados inválidos para agendar consulta.");
        }
        if (!medicos.contains(medico)) {
            throw new IllegalArgumentException("Médico não registado.");
        }
        if (!pacientes.contains(paciente)) {
            throw new IllegalArgumentException("Paciente não registado.");
        }
        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data e hora da consulta não podem ser no passado.");
        }
        if (!horariosDisponiveis.get(medico).contains(dataHora)) {
            throw new IllegalArgumentException("Horário não disponível para este médico.");
        }
        Consulta consulta = new Consulta(dataHora, medico, paciente);
        consultas.add(consulta);
        paciente.adicionarConsulta(consulta);
        removerHorarioDisponivel(medico, dataHora);
    }

    /**
     * Agenda um exame para um paciente.
     * 
     * @param paciente   O paciente.
     * @param dataHora   A data e hora do exame.
     * @param designacao A designação do exame.
     * @param medico     O médico responsável.
     */
    public void agendarExame(Paciente paciente, LocalDateTime dataHora, String designacao, Medico medico) {
        if (paciente == null || dataHora == null || designacao == null || designacao.isEmpty() || medico == null) {
            throw new IllegalArgumentException("Dados inválidos para agendar exame.");
        }
        if (!medicos.contains(medico)) {
            throw new IllegalArgumentException("Médico não registado.");
        }
        if (!pacientes.contains(paciente)) {
            throw new IllegalArgumentException("Paciente não registado.");
        }
        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data e hora do exame não podem ser no passado.");
        }
        if (!horariosDisponiveis.get(medico).contains(dataHora)) {
            throw new IllegalArgumentException("Horário não disponível para este médico.");
        }
        Exame exame = new Exame(dataHora, designacao, medico, paciente);
        exames.add(exame);
        paciente.adicionarExame(exame);
        removerHorarioDisponivel(medico, dataHora);
    }

    /**
     * Remove um horário disponível para um médico.
     * 
     * @param medico   O médico.
     * @param dataHora O horário a ser removido.
     */
    private void removerHorarioDisponivel(Medico medico, LocalDateTime dataHora) {
        horariosDisponiveis.get(medico).remove(dataHora);
    }

    /**
     * Regista os resultados de um exame.
     * 
     * @param exame       O exame.
     * @param observacoes As observações do exame (máximo de 30 caracteres).
     */
    public void registarResultadosExame(Exame exame, String observacoes) {
        if (exame == null || observacoes == null || observacoes.isEmpty()) {
            throw new IllegalArgumentException("Dados inválidos para registar resultados do exame.");
        }
        if (observacoes.length() > 30) {
            throw new IllegalArgumentException("Observações não podem ter mais que 30 caracteres.");
        }
        exame.setObservacoes(observacoes);
    }

    /**
     * Pesquisa um paciente pelo nome.
     * 
     * @param nome O nome do paciente.
     * @return O paciente encontrado ou null se não encontrado.
     */
    public Paciente pesquisarPaciente(String nome) {
        for (Paciente paciente : pacientes) {
            if (paciente.getNome().equalsIgnoreCase(nome)) {
                return paciente;
            }
        }
        return null;
    }

    /**
     * Pesquisa um paciente pelo ID (número do cartão de cidadão).
     * 
     * @param id O ID do paciente.
     * @return O paciente encontrado ou null se não encontrado.
     */
    public Paciente pesquisarPacientePorId(String id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getId().equals(id)) {
                return paciente;
            }
        }
        return null;
    }

    /**
     * Pesquisa um médico pelo nome.
     * 
     * @param nome O nome do médico.
     * @return O médico encontrado ou null se não encontrado.
     */
    public Medico pesquisarMedico(String nome) {
        for (Medico medico : medicos) {
            if (medico.getNome().equalsIgnoreCase(nome)) {
                return medico;
            }
        }
        return null;
    }

    /**
     * Pesquisa um exame pelo paciente e data/hora.
     * 
     * @param paciente O paciente.
     * @param dataHora A data e hora do exame.
     * @return O exame encontrado ou null se não encontrado.
     */
    public Exame pesquisarExame(Paciente paciente, LocalDateTime dataHora) {
        for (Exame exame : exames) {
            if (exame.getPaciente().equals(paciente) && exame.getDataHora().equals(dataHora)) {
                return exame;
            }
        }
        return null;
    }

    /**
     * Exibe o histórico de consultas de um paciente.
     * 
     * @param nomePaciente O nome do paciente.
     */
    public void verHistoricoConsultas(String nomePaciente) {
        Paciente paciente = pesquisarPaciente(nomePaciente);
        if (paciente != null) {
            List<Consulta> consultas = paciente.getConsultas();
            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta encontrada para este paciente.");
            } else {
                System.out.println("Histórico de Consultas:");
                for (Consulta consulta : consultas) {
                    System.out.println(consulta.getDataHora().format(dateTimeFormatter) + " - Médico: " + consulta.getMedico().getNome());
                }
            }
        } else {
            System.out.println("Paciente não encontrado.");
        }
    }

    /**
     * Exibe o histórico de consultas por data e hora.
     * 
     * @param dataHora A data e hora da consulta.
     */
    public void verHistoricoConsultasPorDataHora(LocalDateTime dataHora) {
        System.out.println("Consultas na data e hora: " + dataHora.format(dateTimeFormatter));
        boolean found = false;
        for (Consulta consulta : consultas) {
            if (consulta.getDataHora().equals(dataHora)) {
                System.out.println(consulta.getDataHora().format(dateTimeFormatter) + " - Médico: " + consulta.getMedico().getNome());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nenhuma consulta encontrada para a data e hora: " + dataHora.format(dateTimeFormatter));
        }
    }

    /**
     * Exibe o histórico de exames de um paciente.
     * 
     * @param nomePaciente O nome do paciente.
     */
    public void verHistoricoExames(String nomePaciente) {
        Paciente paciente = pesquisarPaciente(nomePaciente);
        if (paciente != null) {
            List<Exame> exames = paciente.getExames();
            if (exames.isEmpty()) {
                System.out.println("Nenhum exame encontrado para este paciente.");
            } else {
                System.out.println("Histórico de Exames:");
                for (Exame exame : exames) {
                    System.out.println(exame.getDataHora().format(dateTimeFormatter) + " - " + exame.getDesignacao() + " - Médico: " + exame.getMedico().getNome() + " - Resultado: " + exame.getObservacoes());
                }
            }
        } else {
            System.out.println("Paciente não encontrado.");
        }
    }

    /**
     * Pesquisa exames por tipo.
     * 
     * @param tipoExame O tipo de exame.
     */
    public void verHistoricoExamesPorTipo(String tipoExame) {
        System.out.println("Exames do tipo: " + tipoExame);
        boolean found = false;
        for (Exame exame : exames) {
            if (exame.getDesignacao().equalsIgnoreCase(tipoExame)) {
                System.out.println(exame.getDataHora().format(dateTimeFormatter) + " - " + exame.getDesignacao() + " - Médico: " + exame.getMedico().getNome() + " - Resultado: " + exame.getObservacoes());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nenhum exame encontrado para o tipo: " + tipoExame);
        }
    }

    /**
     * Pesquisa exames por data e hora.
     * 
     * @param dataHora A data e hora do exame.
     */
    public void verHistoricoExamesPorDataHora(LocalDateTime dataHora) {
        System.out.println("Exames na data e hora: " + dataHora.format(dateTimeFormatter));
        boolean found = false;
        for (Exame exame : exames) {
            if (exame.getDataHora().equals(dataHora)) {
                System.out.println(exame.getDataHora().format(dateTimeFormatter) + " - " + exame.getDesignacao() + " - Médico: " + exame.getMedico().getNome() + " - Resultado: " + exame.getObservacoes());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nenhum exame encontrado para a data e hora: " + dataHora.format(dateTimeFormatter));
        }
    }

    /**
     * Obtém os horários disponíveis para consultas de um médico.
     * 
     * @param medico O médico.
     * @return Uma lista de horários disponíveis.
     */
    public List<LocalDateTime> obterHorariosDisponiveisConsulta(Medico medico) {
        return new ArrayList<>(horariosDisponiveis.get(medico));
    }

    /**
     * Obtém os horários disponíveis para exames de um médico.
     * 
     * @param medico O médico.
     * @return Uma lista de horários disponíveis para exames.
     */
    public List<LocalDateTime> obterHorariosDisponiveisExame(Medico medico) {
        return new ArrayList<>(horariosDisponiveis.get(medico));
    }
}
