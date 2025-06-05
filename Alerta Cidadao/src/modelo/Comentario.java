package modelo;


import Enums.TipoReacao;
// import interfaces.I_Reagivel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date; // Ou LocalDateTime

public class Comentario implements I_Reagivel {
    private int id;
    private Usuario usuario; // Quem fez o comentário
    private String texto;
    private Date dataHora; // Ou LocalDateTime
    private List<Reacao> reacoes; // Lista de reações neste comentário

    public Comentario(int id, Usuario usuario, String texto, Date dataHora) {
        this.id = id;
        this.usuario = usuario;
        this.texto = texto;
        this.dataHora = dataHora;
        this.reacoes = new ArrayList<>();
    }

    // Getters
    public int getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public String getTexto() { return texto; }
    public Date getDataHora() { return dataHora; }
    public List<Reacao> getReacoes() { return reacoes; }

    @Override
    public void receberReacao(Reacao r) {
        this.reacoes.add(r);
    }

    @Override
    public int getContagemReacoes() {
        return this.reacoes.size();
    }

    // Você pode adicionar métodos específicos para contar likes/dislikes
    public int getLikesCount() {
        return (int) reacoes.stream().filter(r -> r.getTipo() == TipoReacao.LIKE).count();
    }

    public int getDislikesCount() {
        return (int) reacoes.stream().filter(r -> r.getTipo() == TipoReacao.DISLIKE).count();
    }
}