package Alerta_Cidadao.model;



public class Localizacao {
    private int id;
    private double latitude;
    private double longitude;
    private String endereco;
    private int numero;


    public Localizacao(int id, double latitude, double longitude, String endereco, int numero) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.endereco = endereco;
        this.numero = numero;
    }

    public Localizacao(double latitude, double longitude, String endereco, int numero) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.endereco = endereco;
        this.numero = numero;
    }

    public Localizacao() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}