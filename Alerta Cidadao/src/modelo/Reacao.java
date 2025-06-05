package modelo;

import Enums.TipoReacao;
import java.util.Date; 

public class Reacao {
    private int id;
    private TipoReacao tipo;
    private Usuario autor;
    private Date data; 

    public Reacao(int id, TipoReacao tipo, Usuario autor, Date data) {
        this.id = id;
        this.tipo = tipo;
        this.autor = autor;
        this.data = data;
    }

    // Getters
    public int getId() { return id; }
    public TipoReacao getTipo() { return tipo; }
    public Usuario getAutor() { return autor; }
    public Date getData() { return data; }
}
