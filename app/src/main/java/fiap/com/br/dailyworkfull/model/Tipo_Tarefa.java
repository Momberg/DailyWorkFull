package fiap.com.br.dailyworkfull.model;

/**
 * Created by gabri on 12/03/2017.
 */

public class Tipo_Tarefa {

    private int id;
    private String nome;

    public Tipo_Tarefa(int id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public Tipo_Tarefa(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
