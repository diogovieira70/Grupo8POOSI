package ProjetoPoo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável por apresentar o menu da aplicação.
 */
public class Menu {
    private static GerirConsultasExames gestor;
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /**
     * Método principal que inicializa o menu.
     *
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        GerirConsultasExames.inicializarGestor();
        gestor = GerirConsultasExames.getInstance();

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("Menu da Aplicação:");
            System.out.println("1. Registrar Médico");
            System.out.println("2. Registrar Paciente");
            System.out.println("3. Agendar Consulta");
            System.out.println("4. Agendar Exame");
            System.out.println("5. Registrar Resultados de Exame");
            System.out.println("6. Ver Histórico de Consultas");
            System.out.println("7. Ver Histórico de Consultas por Data e Hora");
            System.out.println("8. Ver Histórico de Exames");
            System.out.println("9. Ver Histórico de Exames por Tipo");
            System.out.println("10. Ver Histórico de Exames por Data e Hora");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                scanner.nextLine(); // Consumir a entrada inválida
                continue;
            }

            switch (opcao) {
                case 1:
                    registrarMedico();
                    break;
                case 2:
                    registrarPaciente();
                    break;
                case 3:
                    agendarConsulta();
                    break;
                case 4:
                    agendarExame();
                    break;
                case 5:
                    registrarResultadosExame();
                    break;
                case 6:
                    verHistoricoConsultas();
                    break;
                case 7:
                    verHistoricoConsultasPorDataHora();
                    break;
                case 8:
                    verHistoricoExames();
                    break;
                case 9:
                    verHistoricoExamesPorTipo();
                    break;
                case 10:
                    verHistoricoExamesPorDataHora();
                    break;
                case 0:
                    System.out.println("Saindo da aplicação...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    /**
     * Registra um médico.
     */
    private static void registrarMedico() {
        System.out.print("Nome do médico: ");
        String nome = scanner.nextLine();
        if (!nome.matches("[a-zA-Z\\s]+")) {
            System.out.println("Erro: O nome do médico deve conter apenas caracteres alfabéticos.");
            return;
        }
        System.out.print("Especialidade do médico: ");
        String especialidade = scanner.nextLine();
        if (!especialidade.equalsIgnoreCase("Obstetra") && !especialidade.equalsIgnoreCase("Obstetrícia")) {
            System.out.println("Erro: Especialidade deve ser 'Obstetra' ou 'Obstetrícia'.");
            return;
        }
        Medico medico = new Medico(nome, especialidade);
        gestor.registrarMedico(medico);
        System.out.println("Médico registrado com sucesso!");
    }

    /**
     * Registra um paciente.
     */
    private static void registrarPaciente() {
        System.out.print("ID do paciente (apenas números): ");
        String id = scanner.nextLine();
        if (!id.matches("\\d+")) {
            System.out.println("Erro: O ID do paciente deve conter apenas números.");
            return;
        }
        System.out.print("Nome do paciente: ");
        String nome = scanner.nextLine();
        if (!nome.matches("[a-zA-Z\\s]+")) {
            System.out.println("Erro: O nome do paciente deve conter apenas caracteres alfabéticos.");
            return;
        }
        System.out.print("Data de nascimento do paciente (dd-MM-yyyy): ");
        String dataNascimentoStr = scanner.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false); // Desabilita a análise leniente
        try {
            Date dataNascimento = sdf.parse(dataNascimentoStr);
            Paciente paciente = new Paciente(id, nome, dataNascimento);
            gestor.registrarPaciente(paciente);
            System.out.println("Paciente registrado com sucesso!");
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Use 'dd-MM-yyyy'.");
        }
    }

    /**
     * Agenda uma consulta.
     */
    private static void agendarConsulta() {
        System.out.print("Nome do paciente: ");
        String nomePaciente = scanner.nextLine();
        if (!nomePaciente.matches("[a-zA-Z\\s]+")) {
            System.out.println("Erro: O nome do paciente deve conter apenas caracteres alfabéticos.");
            return;
        }
        Paciente paciente = gestor.pesquisarPaciente(nomePaciente);
        if (paciente != null) {
            System.out.print("Nome do médico: ");
            String nomeMedico = scanner.nextLine();
            if (!nomeMedico.matches("[a-zA-Z\\s]+")) {
                System.out.println("Erro: O nome do médico deve conter apenas caracteres alfabéticos.");
                return;
            }
            Medico medico = gestor.pesquisarMedico(nomeMedico);
            if (medico != null) {
                List<LocalDateTime> horariosDisponiveis = gestor.obterHorariosDisponiveisConsulta(medico);
                System.out.println("Horários disponíveis para consultas:");
                for (int i = 0; i < horariosDisponiveis.size(); i++) {
                    System.out.println((i + 1) + ". " + horariosDisponiveis.get(i).format(dateTimeFormatter));
                }
                System.out.print("Escolha um horário (número): ");
                try {
                    int escolha = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha
                    if (escolha > 0 && escolha <= horariosDisponiveis.size()) {
                        LocalDateTime dataHora = horariosDisponiveis.get(escolha - 1);
                        gestor.agendarConsulta(paciente, dataHora, medico);
                        System.out.println("Consulta agendada com sucesso!");
                    } else {
                        System.out.println("Escolha inválida.");
                    }
                } catch (Exception e) {
                    System.out.println("Entrada inválida. Por favor, escolha um número válido.");
                    scanner.nextLine(); // Consumir a entrada inválida
                }
            } else {
                System.out.println("Médico não encontrado.");
            }
        } else {
            System.out.println("Paciente não encontrado.");
        }
    }

    /**
     * Agenda um exame.
     */
    private static void agendarExame() {
        System.out.print("Nome do paciente: ");
        String nomePaciente = scanner.nextLine();
        if (!nomePaciente.matches("[a-zA-Z\\s]+")) {
            System.out.println("Erro: O nome do paciente deve conter apenas caracteres alfabéticos.");
            return;
        }
        Paciente paciente = gestor.pesquisarPaciente(nomePaciente);
        if (paciente != null) {
            System.out.print("Nome do médico: ");
            String nomeMedico = scanner.nextLine();
            if (!nomeMedico.matches("[a-zA-Z\\s]+")) {
                System.out.println("Erro: O nome do médico deve conter apenas caracteres alfabéticos.");
                return;
            }
            Medico medico = gestor.pesquisarMedico(nomeMedico);
            if (medico != null) {
                List<LocalDateTime> horariosDisponiveis = gestor.obterHorariosDisponiveisExame(medico);
                System.out.println("Horários disponíveis para exames:");
                for (int i = 0; i < horariosDisponiveis.size(); i++) {
                    System.out.println((i + 1) + ". " + horariosDisponiveis.get(i).format(dateTimeFormatter));
                }
                System.out.print("Escolha um horário (número): ");
                try {
                    int escolha = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha
                    if (escolha > 0 && escolha <= horariosDisponiveis.size()) {
                        LocalDateTime dataHora = horariosDisponiveis.get(escolha - 1);
                        System.out.print("Designação do Exame (por exemplo, Ecografia): ");
                        String designacao = scanner.nextLine();
                        gestor.agendarExame(paciente, dataHora, designacao, medico);
                        System.out.println("Exame agendado com sucesso!");
                    } else {
                        System.out.println("Escolha inválida.");
                    }
                } catch (Exception e) {
                    System.out.println("Entrada inválida. Por favor, escolha um número válido.");
                    scanner.nextLine(); // Consumir a entrada inválida
                }
            } else {
                System.out.println("Médico não encontrado.");
            }
        } else {
            System.out.println("Paciente não encontrado.");
        }
    }

    /**
     * Registra os resultados de um exame.
     */
    private static void registrarResultadosExame() {
        System.out.print("Nome do paciente: ");
        String nomePaciente = scanner.nextLine();
        if (!nomePaciente.matches("[a-zA-Z\\s]+")) {
            System.out.println("Erro: O nome do paciente deve conter apenas caracteres alfabéticos.");
            return;
        }
        Paciente paciente = gestor.pesquisarPaciente(nomePaciente);
        if (paciente != null) {
            System.out.print("Data e Hora do Exame (dd-MM-yyyy HH:mm): ");
            String dataHoraStr = scanner.nextLine();
            try {
                LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, dateTimeFormatter);
                Exame exame = gestor.pesquisarExame(paciente, dataHora);
                if (exame != null) {
                    System.out.print("Resultado do Exame (até 30 caracteres): ");
                    String resultado = scanner.nextLine();
                    gestor.registrarResultadosExame(exame, resultado);
                    System.out.println("Resultado registrado com sucesso!");
                } else {
                    System.out.println("Exame não encontrado.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data e hora inválido. Use 'dd-MM-yyyy HH:mm'.");
            }
        } else {
            System.out.println("Paciente não encontrado.");
        }
    }

    /**
     * Exibe o histórico de consultas de um paciente.
     */
    private static void verHistoricoConsultas() {
        System.out.print("Nome do Paciente: ");
        String nomePaciente = scanner.nextLine();
        if (!nomePaciente.matches("[a-zA-Z\\s]+")) {
            System.out.println("Erro: O nome do paciente deve conter apenas caracteres alfabéticos.");
            return;
        }
        if (gestor.pesquisarPaciente(nomePaciente) != null) {
            gestor.verHistoricoConsultas(nomePaciente);
        } else {
            System.out.println("Paciente não encontrado.");
        }
    }

    /**
     * Exibe o histórico de consultas por data e hora.
     */
    private static void verHistoricoConsultasPorDataHora() {
        System.out.print("Data e Hora da Consulta (dd-MM-yyyy HH:mm): ");
        String dataHoraStr = scanner.nextLine();
        try {
            LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, dateTimeFormatter);
            gestor.verHistoricoConsultasPorDataHora(dataHora);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data e hora inválido. Use 'dd-MM-yyyy HH:mm'.");
        }
    }

    /**
     * Exibe o histórico de exames de um paciente.
     */
    private static void verHistoricoExames() {
        System.out.print("Nome do Paciente: ");
        String nomePaciente = scanner.nextLine();
        if (!nomePaciente.matches("[a-zA-Z\\s]+")) {
            System.out.println("Erro: O nome do paciente deve conter apenas caracteres alfabéticos.");
            return;
        }
        Paciente paciente = gestor.pesquisarPaciente(nomePaciente);
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
     * Exibe o histórico de exames por tipo.
     */
    private static void verHistoricoExamesPorTipo() {
        System.out.print("Tipo de Exame: ");
        String tipoExame = scanner.nextLine();
        gestor.verHistoricoExamesPorTipo(tipoExame);
    }

    /**
     * Exibe o histórico de exames por data e hora.
     */
    private static void verHistoricoExamesPorDataHora() {
        System.out.print("Data e Hora do Exame (dd-MM-yyyy HH:mm): ");
        String dataHoraStr = scanner.nextLine();
        try {
            LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, dateTimeFormatter);
            gestor.verHistoricoExamesPorDataHora(dataHora);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data e hora inválido. Use 'dd-MM-yyyy HH:mm'.");
        }
    }
}
