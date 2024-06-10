package ProjetoPoo;

public class Medico {
    private String nome;
    private String especialidade;

    /**
     * Construtor da classe Medico.
     *
     * @param nome O nome do médico.
     * @param especialidade A especialidade do médico.
     */
    public Medico(String nome, String especialidade) {
        this.nome = nome;
        this.especialidade = especialidade;
    }

    /**
     * getters e setters
     * @return
     */

    public String getNome() {
        return nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "nome='" + nome + '\'' + ", especialidade='" + especialidade + '\'' + '}';
    }
}

