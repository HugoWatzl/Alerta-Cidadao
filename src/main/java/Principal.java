import Alerta_Cidadao.model.*;
import Alerta_Cidadao.enums.*;
import BD.ConnectionFactory;
import DAO.AlertaDAO;
import DAO.ComentarioDAO;
import DAO.UsuarioDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Principal {
    public static void main(String[] args){


        System.out.println("--- INÍCIO DA EXECUÇÃO EM MEMÓRIA ---\n");
        Usuario criadorDoAlerta = new Usuario(1, "Hugo", "hg@email.com", "senha1");
        Usuario outroUsuario = new Usuario(2, "Eduardo", "dus@email.com", "senha2");
        Localizacao localDoFato = new Localizacao(154, -22.90, -43.17, "Rua Principal", 5);
        Usuario CriadorAlerta2 = new Usuario(3, "Hugo Watzl", "hugo@email.com", "senha3");
        Localizacao Local2 = new Localizacao(23,-33.12, -86.01, "Av Romero", 1002);

        //  alerta
        Alerta a1 = new Alerta(
                589,
                TipoAlerta.ASSALTO,
                "Assalto a mão armada ocorreu perto da lojas Americanas em copacabana na Princesa Isabel.",
                Date.valueOf(LocalDate.now()),
                Time.valueOf(LocalTime.now()),
                localDoFato,
                criadorDoAlerta);
        System.out.println("Alerta criado: " + a1.getDescricao());
        System.out.println("Criado por: " + a1.getCriador().getNome());


        Alerta a2 = new Alerta(
                30,
                TipoAlerta.ARRASTAO,
                "Arrastao na praia do arpoador/ipanema posto 8",
                Date.valueOf(LocalDate.now()),
                Time.valueOf(LocalTime.now()),
                Local2,
                CriadorAlerta2
        );


        // COMENTARIO
        Comentario c1 = new Comentario(
                731,
                outroUsuario,
                "Eu tambem vi o ocorrido, confirmo a informação.",
                new java.util.Date()
        );
        a1.adicionarComentario(c1);
        System.out.println("Novo comentario de " + c1.getUsuario().getNome() + ": " + c1.getTexto());

        //  Likes
        Reacao  l0 = new Reacao(
                1,
                TipoReacao.LIKE,
                criadorDoAlerta,
                Date.valueOf(LocalDate.now())
        );
        Reacao l1 = new Reacao(
                2,
                TipoReacao.LIKE,
                criadorDoAlerta,
                Date.valueOf(LocalDate.now())
        );

        c1.receberReacao(l0);
        c1.receberReacao(l1);
        int C1likes = c1.getContagemReacoes(TipoReacao.LIKE);
        System.out.println("Comentário possui " + C1likes + " like(s).");


        // DESIKES no comentario
        Reacao d1 = new Reacao(
                301,
                TipoReacao.DESLIKE,
                criadorDoAlerta,
                Date.valueOf(LocalDate.now())
        );

        c1.receberReacao(d1); ;
        int totalDesikes = c1.getContagemReacoes(TipoReacao.DESLIKE);
        System.out.println("Comentário possui " + totalDesikes + " DESlike(s).");

        //reacao alerta
        Reacao l2 = new Reacao(
                3,
                TipoReacao.LIKE,
                CriadorAlerta2,
                Date.valueOf(LocalDate.now())
        );
        a2.receberReacao(l2);
        int A2Likes = a2.getContagemReacoes(TipoReacao.LIKE);
        System.out.println("Alerta "+a2.getId()+" possui " + A2Likes + " like(s).");



        // votando
        a1.receberVoto(criadorDoAlerta, true);
        a1.receberVoto(outroUsuario, false);
        a1.receberVoto(CriadorAlerta2, false);
        System.out.println("Total de votos recebidos: " + a1.getVotacoes().size());
        double confiabilidade = a1.calcularConfiabilidade();
        System.out.printf("Confiabilidade do alerta %d: %.2f%%\n", a1.getId(), confiabilidade);


        a2.receberVoto(criadorDoAlerta, true);
        a2.receberVoto(outroUsuario, true);
        a2.receberVoto(CriadorAlerta2, false);
        System.out.println("Total de votos recebidos: " + a2.getVotacoes().size());
        double confiabilidade2 = a2.calcularConfiabilidade();
        System.out.printf("Confiabilidade do alerta %d: %.2f%%\n", a2.getId(), confiabilidade2);





        System.out.println("--- INÍCIO DA INTERAÇÃO COM O BANCO DE DADOS (SQL) \n\n---");
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection conexao = factory.recuperaConexao()) {

            //ETAPA DE PREPARAÇÃO COMPLETA DO BANCO
            System.out.println("Preparando o banco de dados para o teste...");
            try (java.sql.Statement stmt = conexao.createStatement()) {

                // Desativa temporariamente a verificação de chave estrangeira para limpar tudo
                stmt.execute("SET FOREIGN_KEY_CHECKS=0;");

                //  Limpa os dados das execuções anteriores com TRUNCATE
                stmt.executeUpdate("TRUNCATE TABLE tb_comentario;");
                stmt.executeUpdate("TRUNCATE TABLE tb_alerta;");
                stmt.executeUpdate("TRUNCATE TABLE tb_usuario;");

                   //Garante que a localização sempre exista (id=1)
                String sqlLocalizacao = "INSERT INTO tb_localizacao (id, latitude, longitude, endereco, numero) " +
                        "VALUES (1, -22.9843, -43.2045, 'Rua Jardim Botânico', 100) " +
                        "ON DUPLICATE KEY UPDATE id=1;";
                stmt.executeUpdate(sqlLocalizacao);

                //Reativa a verificação de chave estrangeira
                stmt.execute("SET FOREIGN_KEY_CHECKS=1;");

                System.out.println("Ambiente de teste preparado com sucesso.");
            }





            // --- DEMONSTRAÇÃO DOS MÉTODOS DAO ---
            System.out.println("\n--- 1. DEMONSTRANDO: salvar() ---");
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
            AlertaDAO alertaDAO = new AlertaDAO(conexao);
            ComentarioDAO comentarioDAO = new ComentarioDAO(conexao);

            // Criando dois usuários
            Usuario usuarioAna = new Usuario(0, "Anis", "anis@email.com", "senha6");
            usuarioDAO.salvar(usuarioAna);
            System.out.println("Usuário salvo: " + usuarioAna.getNome() + " (ID: " + usuarioAna.getId() + ")");

            Usuario usuarioBeto = new Usuario(0, "Betin", "beto@email.com", "senha5");
            usuarioDAO.salvar(usuarioBeto);
            System.out.println("Usuário salvo: " + usuarioBeto.getNome() + " (ID: " + usuarioBeto.getId() + ")");

            Usuario usuarioHugo = new Usuario(0, "Hugo Watzl", "hg@email.com", "senha6");
            usuarioDAO.salvar(usuarioHugo);
            System.out.println("Usuário salvo: " + usuarioAna.getNome() + " (ID: " + usuarioAna.getId() + ")");

            // cria um alerta
            Localizacao localizacao = new Localizacao();
            localizacao.setId(1);
            Alerta alertaDaAna = new Alerta();
            alertaDaAna.setTipo(TipoAlerta.ASSALTO);
            alertaDaAna.setDescricao("Assalto na rua principal.");
            alertaDaAna.setData(Date.valueOf(LocalDate.now()));
            alertaDaAna.setHora(Time.valueOf(LocalTime.now()));
            alertaDaAna.setLocalizacao(localizacao);
            alertaDaAna.setCriador(usuarioAna);
            alertaDaAna.setEstaAtivo(true);
            alertaDAO.salvar(alertaDaAna);
            System.out.println(usuarioAna.getNome() + " criou um alerta com ID: " + alertaDaAna.getId());


            System.out.println("\n- 2. DEMONSTRANDO: buscarPorId() -");
            
            Usuario betoDoBanco = (Usuario) usuarioDAO.buscarPorId(usuarioBeto.getId());
            if (betoDoBanco != null) {
                System.out.println("Busca por ID bem-sucedida: Encontrado " + betoDoBanco.getNome());
            } else {
                System.out.println("Falha na busca por ID.");
            }


            System.out.println("\n-- 3. DEMONSTRANDO: atualizar() ");
            System.out.println("Nome de Betin ANTES da atualização: " + betoDoBanco.getNome());
            betoDoBanco.setNome("Roberto"); 
            usuarioDAO.atualizar(betoDoBanco);
        
            Usuario betoAtualizado = (Usuario) usuarioDAO.buscarPorId(betoDoBanco.getId());
            System.out.println("Nome de Beto DEPOIS da atualização: " + betoAtualizado.getNome());


            System.out.println("\n- 4. DEMONSTRANDO: excluir() ");
            System.out.println("Excluindo o usuário '" + usuarioAna.getNome() + "' (ID: " + usuarioAna.getId() + ")");
            // Para excluir Ana, primeiro precisamos excluir os alertas que ela criou.
            alertaDAO.excluir(alertaDaAna.getId());
            usuarioDAO.excluir(usuarioAna.getId());
            // buscar Ana para confirmar a exclusão
            Usuario anaDoBanco = (Usuario) usuarioDAO.buscarPorId(usuarioAna.getId());
            if (anaDoBanco == null) {
                System.out.println("Confirmação: Usuário '" + usuarioAna.getNome() + "' não foi encontrado. Exclusão funcionou.");
            } else {
                System.out.println("ERRO: A exclusão falhou.");
            }


        } catch (SQLException e) {
            System.err.println("ERRO: Não foi possível conectar ou operar o banco de dados.");
            e.printStackTrace();
        }
    }
}


