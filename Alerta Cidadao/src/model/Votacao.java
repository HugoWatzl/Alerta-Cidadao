package model;

import java.time.LocalDateTime;
import java.util.Date; // O diagrama usa Date. Em Java moderno, prefira java.time.LocalDateTime

public class Votacao {
    private int id;
    private Usuario usuario;
    private Alerta alerta;
    private boolean aindaAcontecendo;
    private Date data; // Ou LocalDateTime

    public Votacao(int id, Usuario usuario, Alerta alerta, boolean aindaAcontecendo, Date data) {
        this.id = id;
        this.usuario = usuario;
        this.alerta = alerta;
        this.aindaAcontecendo = aindaAcontecendo;
        this.data = data;
    }

    // Getters
    public int getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Alerta getAlerta() { return alerta; }
    public boolean isAindaAcontecendo() { return aindaAcontecendo; }
    public Date getData() { return data; }
}