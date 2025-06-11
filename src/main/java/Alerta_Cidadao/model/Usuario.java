package Alerta_Cidadao.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Pessoa {
    private List<Alerta> alertasCriados;
    private List<Comentario> comentarios;

    // Construtor padrão corrigido
    public Usuario() {
        super(0, null, null, null); // Chama o construtor de Pessoa com valores padrão
        this.alertasCriados = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    // Adicione um construtor com parâmetros
    public Usuario(int id, String nome, String email, String senha) {
        super(id, nome, email, senha);
        this.alertasCriados = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    // Métodos da classe
    public void adicionarAlerta(Alerta alerta) {
        this.alertasCriados.add(alerta);
    }

    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }
}
