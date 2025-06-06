package Alerta_Cidadao;
import Alerta_Cidadao.model.*;
import Alerta_Cidadao.enums.*;
import Alerta_Cidadao.interfaces.I_Reagivel;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.sql.Time;

public class Principal {
    public static void main(String[] args){


        Usuario criadorDoAlerta = new Usuario(1, "Hugo farias", "hg@email.com", "senha1");
        Usuario outroUsuario = new Usuario(2, "Eduardo Jacob", "dusts@gmail.com", "senha2");
        Localizacao localDoFato = new Localizacao(154, -22.90, -43.17, "Rua Principal", 5);
        Usuario CriadorAlerta2 = new Usuario(3, "Augusto amorim", "AUg@email.com", "senha3");
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
                "Arrastao na praia do arpoador/ipanema",
                Date.valueOf(LocalDate.now()),
                Time.valueOf(LocalTime.now()),
                Local2,
                CriadorAlerta2
        );


      // COMENTARIOS
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
    }
}
