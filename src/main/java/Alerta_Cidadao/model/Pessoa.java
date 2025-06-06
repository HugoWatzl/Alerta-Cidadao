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

        public String getNome() { return nome; }



    }

