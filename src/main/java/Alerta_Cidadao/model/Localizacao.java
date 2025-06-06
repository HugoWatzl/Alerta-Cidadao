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


    public int getId() { return id; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getEndereco() { return endereco; }
    public int getNumero() { return numero; }

}