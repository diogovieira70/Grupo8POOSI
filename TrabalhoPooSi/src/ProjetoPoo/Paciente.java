package ProjetoPoo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe que representa um paciente.
 */
public class Paciente {
    private String id;
    private String nome;
    private Date dataNascimento;
    private List<Consulta> consultas;
    private List<Exame> exames;

    /**
     * Construtor da classe Paciente.
     *
     * @param nome O nome do paciente.
     */
    public Paciente(String nome) {
        this.nome = nome;
        this.consultas = new ArrayList<>();
        this.exames = new ArrayList<>();
    }

    /**
     * Construtor da classe Paciente.
     *
     * @param id O ID do paciente.
     * @param nome O nome do paciente.
     * @param dataNascimento A data de nascimento do paciente.
     */
    public Paciente(String id, String nome, Date dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.consultas = new ArrayList<>();
        this.exames = new ArrayList<>();
    }

    /**
     * getters e setters
     * @return
     */

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public List<Exame> getExames() {
        return exames;
    }

    public void adicionarConsulta(Consulta consulta) {
        this.consultas.add(consulta);
    }

    public void adicionarExame(Exame exame) {
        this.exames.add(exame);
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                '}';
    }
}
