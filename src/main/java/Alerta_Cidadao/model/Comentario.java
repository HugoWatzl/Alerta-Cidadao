package Alerta_Cidadao.model;

import Alerta_Cidadao.enums.TipoReacao;
import Alerta_Cidadao.interfaces.I_Reagivel;

import java.util.ArrayList; // Adicionar esta linha
import java.util.List;
import java.util.Date;

public class Comentario implements I_Reagivel {
    private int id;
    private Usuario usuario;
    private String texto;
    private Date dataHora;
    private List<Reacao> reacoes;
    private int IdAlerta;

    public Comentario(int id, Usuario usuario, String texto, Date dataHora) {
        this.id = id;
        this.usuario = usuario;
        this.texto = texto;
        this.dataHora = dataHora;
        this.reacoes = new ArrayList<>(); // Adicionar esta linha para inicializar a lista
    }
    public Comentario(){
        this.reacoes = new ArrayList<>(); // Inicializar aqui também
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public List<Reacao> getReacoes() {
        return reacoes;
    }

    public void setReacoes(List<Reacao> reacoes) {
        this.reacoes = reacoes;
    }
    public int getIdAlerta() {
        return IdAlerta;
    }

    public void setIdAlerta(int idAlerta) {
        IdAlerta = idAlerta;
    }

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


