package Alerta_Cidadao.interfaces;

import Alerta_Cidadao.enums.TipoReacao;
import Alerta_Cidadao.model.Reacao;

public interface I_Reagivel {

    void receberReacao(Reacao r);

    int getContagemReacoes(TipoReacao tipo);

}
