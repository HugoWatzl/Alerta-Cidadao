package Alerta_Cidadao.model;

import Alerta_Cidadao.enums.TipoReacao;
import Alerta_Cidadao.interfaces.I_Reagivel;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Comentario implements I_Reagivel {
    private int id;
    private Usuario usuario;
    private String texto;
    private Date dataHora;
    private List<Reacao> reacoes;

    public Comentario(int id, Usuario usuario, String texto, Date dataHora) {
        this.id = id;
        this.usuario = usuario;
        this.texto = texto;
        this.dataHora = dataHora;
        this.reacoes = new ArrayList<>();
    }


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
    public int getContagemReacoes(TipoReacao tipo) {
        int contador = 0;
        for (Reacao r : this.reacoes) {
            if (r.getTipo() == tipo) {
                contador++;}
        }
        return contador;
    }

}