package Alerta_Cidadao.model;


    public abstract class Pessoa {
        protected int id;
        protected String nome;
        protected String email;
        protected String senha;


        public Pessoa(int id, String nome, String email, String senha) {
            this.id = id;
            this.nome = nome;
            this.email = email;
            this.senha = senha;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public String getSenha(){
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

