package DAO;

import java.util.ArrayList;

public interface BaseDAO {
    public void salvar(Object objeto);
    public Object buscarPorId(int id);
    public void atualizar(Object objeto);
    public void excluir(int id);
}
