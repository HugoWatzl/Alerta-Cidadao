package model;

public class Pessoa {
    public abstract static class Pessoa {
        protected int id;
        protected String nome;
        protected String email;
        protected String senha;

        // Construtor
        public Pessoa(int id, String nome, String email, String senha) {
            this.id = id;
            this.nome = nome;
            this.email = email;
            this.senha = senha;
        }

        // Getters
        public int getId() { return id; }
        public String getNome() { return nome; }
        public String getEmail() { return email; }


    }
}
