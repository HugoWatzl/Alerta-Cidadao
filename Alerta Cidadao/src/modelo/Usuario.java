package modelo;


// import com.seuprojeto.alertacidadao.enums.TipoReacao;
// import com.seuprojeto.alertacidadao.interfaces.I_Reagivel; // Pode ser necessário

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Pessoa {
    private List<Alerta> alertasCriados;
    private List<Comentario> comentarios;

    public Usuario(int id, String nome, String email, String senha) {
        super(id, nome, email, senha);
        this.alertasCriados = new ArrayList<>();// nao faz sentido no contrutor ter os alertas e comentarios criados do usuario(hg)
        this.comentarios = new ArrayList<>();
    }

    public void adicionarAlerta(Alerta alerta) {
        this.alertasCriados.add(alerta);
    }

    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }



    // Você também pode ter métodos para o usuário realizar votação ou reações,
    // que chamariam métodos nas instâncias de Alerta ou Comentario.
    // Ex: public void votar(Alerta alerta, boolean aindaAcontecendo) { alerta.receberVoto(aindaAcontecendo); }
    // Ex: public void reagir(I_Reagivel item, TipoReacao tipo) { item.receberReacao(new Reacao(this, tipo)); }
}
