package Alerta_Cidadao.model;



import Alerta_Cidadao.enums.TipoAlerta;
import Alerta_Cidadao.enums.TipoReacao;
import Alerta_Cidadao.interfaces.I_Reagivel;
import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Alerta implements I_Reagivel {
    private int id;
    private TipoAlerta tipo;
    private String descricao;
    private Date data;
    private Time hora;
    private Localizacao localizacao;
    private Usuario criador;
    private List<Votacao> votacoes;
    private List<Reacao> reacoes;
    private List<Comentario> comentarios;
    private boolean estaAtivo;


    public Alerta(int id, TipoAlerta tipo, String descricao, Date data, Time hora, Localizacao localizacao, Usuario criador) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.data = data;
        this.hora = hora;
        this.localizacao = localizacao;
        this.criador = criador;
        this.votacoes = new ArrayList<>();
        this.reacoes = new ArrayList<>();
        this.comentarios = new ArrayList<>();
        this.estaAtivo = true; // Alerta é ativo  ao ser criado
    }


        public int getId() { return id; }
        public TipoAlerta getTipo() { return tipo; }
        public String getDescricao() { return descricao; }
        public Date getData() { return data; }
        public Time getHora() { return hora; }
        public Localizacao getLocalizacao() { return localizacao; }
        public Usuario getCriador() { return criador; }
        public List<Votacao> getVotacoes() { return votacoes; }
        public List<Reacao> getReacoes() { return reacoes; }
        public List<Comentario> getComentarios() { return comentarios; }
        public boolean isEstaAtivo() { return estaAtivo; }

    public double calcularConfiabilidade() {
        if (votacoes.isEmpty()) {//isempty verificva se a lista esta fazia
            System.out.println("Nenhuma votação cadastrada.");
            return 0.;
        }
        int votosAtivos = 0;
        for(Votacao v : votacoes) {
            if(v.isAindaAcontecendo()) {
                votosAtivos++;}
        }return (double) votosAtivos / votacoes.size() * 100.0;
    }


    public void receberVoto(Boolean voto) {
        if (estaAtivo) {
            Votacao vot;
            vot = new Votacao(voto);
            votacoes.add(vot);
        }
    }

    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }

    @Override
    public void receberReacao(Reacao r) {
        this.reacoes.add(r);
    }

    @Override
    public int getContagemReacoes(TipoReacao tipo) {
        int qnt_Reacao = 0;

        for (Reacao r : this.reacoes) {
            if (r.getTipo() == tipo) { qnt_Reacao++;}
        }
        return qnt_Reacao;
    }

    }
