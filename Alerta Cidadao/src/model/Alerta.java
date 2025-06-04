package model;

 import Enums.AlertType;
 import Enums.TipoReacao;
// import interfaces.I_Reagivel;
// import interfaces.I_Votavel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date; 
import java.util.List;



public class Alerta implements I_Votavel, I_Reagivel {
    private int id;
    private AlertType tipo; 
    private String descricao;
    private Date data;
    private LocalDateTime hora;
    private Localizacao localizacao;
    private Usuario criador;
    private List<Votacao> votacoes;
    private List<Reacao> reacoes;
    private List<Comentario> comentarios;
    private boolean estaAtivo;

    public Alerta(int id, AlertType tipo, String descricao, Date data, LocalDateTime hora, Localizacao localizacao, Usuario criador) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.data = data;
        this.hora = hora;
        this.localizacao = new Localizacao[];
        this.criador = criador;
        this.votacoes = new ArrayList<>();
        this.reacoes = new ArrayList<>();
        this.comentarios = new ArrayList<>();
        this.estaAtivo = true; // Alerta é ativo por padrão ao ser criado
    }

    // Gets
    public int getId() { return id; }
    public AlertType getTipo() { return tipo; }
    public String getDescricao() { return descricao; }
    public Date getData() { return data; }
    public LocalDateTime getHora() { return hora; }
    public Localizacao getLocalizacao() { return localizacao; }
    public Usuario getCriador() { return criador; }
    public List<Votacao> getVotacoes() { return votacoes; }
    public List<Reacao> getReacoes() { return reacoes; }
    public List<Comentario> getComentarios() { return comentarios; }
    public boolean isEstaAtivo() { return estaAtivo; }

    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }

    // Implementação das Interfaces 

    @Override
    public void adicionarVotacao(Votacao v) {
        this.votacoes.add(v);
        receberVoto(v.isAindaAcontecendo()); // Atualiza o estado interno e confiabilidade
    }

    @Override
    public void receberVoto(boolean aindaAcontecendo) {
        // Isso é uma simplificação. A lógica real seria:
        // Contar votos "ativo" e "resolvido" e decidir se o alerta está ativo.
        // A confiabilidade é baseada nesses votos.
        calcularConfiabilidade(); // Recalcula a confiabilidade
        // E o estado ativo do alerta pode ser atualizado aqui com base na confiabilidade
        // Ex: if (calcularConfiabilidade() < 0.3) this.estaAtivo = false;
    }

    @Override
    public double calcularConfiabilidade() {
        int votosAtivos = 0;
        int votosResolvidos = 0;
        for (Votacao votacao : votacoes) {
            if (votacao.isAindaAcontecendo()) {
                votosAtivos++;
            } else {
                votosResolvidos++;
            }
        }

        if (votosAtivos + votosResolvidos == 0) {
            return 0.5; // Neutro se não houver votos
        } else {
            double confiabilidade = (double) votosAtivos / (votosAtivos + votosResolvidos);
            // Defina um limiar para marcar como inativo
            if (confiabilidade < 0.2 && (votosAtivos + votosResolvidos > 5)) { // Exemplo: se muitos votaram resolvido
                this.estaAtivo = false;
            } else {
                this.estaAtivo = true; 
            }
            return confiabilidade;
        }
    }

    @Override
    public void receberReacao(Reacao r) {
        this.reacoes.add(r);
    }

    @Override
    public int getContagemReacoes() {
        return this.reacoes.size();
    }

    // Métodos para contar likes/dislikes específicos para o Alerta
    public int getLikesCount() {
        return (int) reacoes.stream().filter(r -> r.getTipo() == TipoReacao.LIKE).count();
    }

    public int getDislikesCount() {
        return (int) reacoes.stream().filter(r -> r.getTipo() == TipoReacao.DISLIKE).count();
    }
}
