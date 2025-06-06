package Alerta_Cidadao;
import Alerta_Cidadao.model.*;
import Alerta_Cidadao.enums.*;
import Alerta_Cidadao.interfaces.I_Reagivel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class Principal {
    public static void main(String[] args){

        // 1. Cria usuários e uma localização
        Usuario criadorDoAlerta = new Usuario(1, "Hugo farias", "hg@email.com", "senha1");
        Usuario outroUsuario = new Usuario(2, "Eduardo", "dusts@gmail.com", "senha2");
        Localizacao localDoFato = new Localizacao(154, -22.90, -43.17, "Rua Principal", 5);

        // 2. Cria um alerta
        Alerta alertaDeAssalto = new Alerta(
                589,
                TipoAlerta.ASSALTO,
                "Assalto a mão armada ocorreu perto da lojas Americanas em copacabana na Princes Isabel.",
                Date.valueOf(LocalDate.now()),
                Time.valueOf(LocalTime.now()),
                localDoFato,
                criadorDoAlerta);
        System.out.println("Alerta criado: " + alertaDeAssalto.getDescricao());
        System.out.println("Criado por: " + alertaDeAssalto.getCriador().getNome());


        // 3. Adiciona um comentário ao alerta
        Comentario comentarioConfirmando = new Comentario(
                731,
                outroUsuario,
                "Eu também vi o ocorrido, confirmo a informação.",
                new java.util.Date()
        );
        alertaDeAssalto.adicionarComentario(comentarioConfirmando);
        System.out.println("Novo comentário de " + comentarioConfirmando.getUsuario().getNome() + ": " + comentarioConfirmando.getTexto());


        // 4. Adiciona uma reação ao comentário
        Reacao likeNoComentario = new Reacao(
                143,
                TipoReacao.LIKE,
                criadorDoAlerta,
                Date.valueOf(LocalDate.now())
        );
        comentarioConfirmando.receberReacao(likeNoComentario);
        int totalLikes = comentarioConfirmando.getContagemReacoes(TipoReacao.LIKE);
        System.out.println("O comentário agora tem " + totalLikes + " like(s).");







    }
}
