package Alerta_Cidadao.model;



 import java.util.ArrayList;
import java.util.List;

public class Usuario extends Pessoa {
    private List<Alerta> alertasCriados;
    private List<Comentario> comentarios;

    public Usuario() {
        super(id, nome, email, senha);
        this.alertasCriados = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }


    public void adicionarAlerta(Alerta alerta) {
        this.alertasCriados.add(alerta);
    }

    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }




}
