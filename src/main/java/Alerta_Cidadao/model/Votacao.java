package Alerta_Cidadao.model;

import java.sql.Date;
import java.sql.Time;

public class Votacao {
    private int id;
    private Usuario usuario;
    private Alerta alerta;
    private boolean aindaAcontecendo;
    private Date data;
    private Time hora;
    private final Boolean voto;// true - sim , false - nao , null sem voto

    public Votacao(int id, Usuario usuario, Alerta alerta, Date data, Time hora,Boolean voto) {
        this.id = id;
        this.usuario = usuario;
        this.alerta = alerta;
        this.data = data;
        this.hora = hora;
        this.aindaAcontecendo = true;//por patrao comeca true
        this.voto = voto ;
    }

    public Votacao(Usuario usuario,Boolean voto){
        this.usuario = usuario;
        this.voto = voto;
        this.aindaAcontecendo = voto;
    }

    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public Alerta getAlerta() {
        return alerta;
    }

    public Date getData() {
        return data;
    }

    public Time getHora() {
        return hora;
    }

    public boolean isAindaAcontecendo() {
        return aindaAcontecendo;
    }


}
