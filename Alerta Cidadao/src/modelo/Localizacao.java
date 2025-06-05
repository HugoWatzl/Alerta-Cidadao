package modelo;


public class Localizacao {
    private double latitude;
    private double longitude;
    private String endereco;
    private int numero;

    public Localizacao(double latitude, double longitude, String endereco, int numero) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.endereco = endereco;
        this.numero = numero;
    }

    // Getters
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getEndereco() { return endereco; }
    public int getNumero() { return numero; }

    // Setters (se necessário)
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public void setNumero(int numero) { this.numero = numero; }
}