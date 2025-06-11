package DAO;

import BD.ConnectionFactory;
import Alerta_Cidadao.model.Alerta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Alerta_Cidadao.model.Localizacao;
import Alerta_Cidadao.model.Usuario;

public class AlertaDAO implements BaseDAO {

    private Connection connection;

    public AlertaDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void salvar(Object objeto) {
        if (!(objeto instanceof Alerta)) {
            throw new IllegalArgumentException("Objeto deve ser do tipo Alerta.");
        }
        Alerta alerta = (Alerta) objeto;

        String sql = "INSERT INTO tb_alerta (tipo, descricao, data, hora, esta_ativo, id_localizacao, id_criador) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, String.valueOf(alerta.getTipo()));
            ps.setString(2, alerta.getDescricao());
            ps.setDate(3, new java.sql.Date(alerta.getData().getTime()));
            ps.setTime(4, alerta.getHora());
            ps.setBoolean(5, alerta.isEstaAtivo());
            ps.setInt(6, alerta.getLocalizacao().getId());
            ps.setInt(7, alerta.getCriador().getId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    alerta.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object buscarPorId(int id) {
        String sql = "SELECT id, tipo, descricao, data, hora, esta_ativo, id_localizacao, id_criador FROM tb_alerta WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Alerta alerta = new Alerta();
                alerta.setId(rs.getInt("id"));
                alerta.setTipo(Enum.valueOf(Alerta_Cidadao.enums.TipoAlerta.class, rs.getString("tipo")));
                alerta.setDescricao(rs.getString("descricao"));
                alerta.setData(rs.getDate("data"));
                alerta.setHora(rs.getTime("hora"));
                alerta.setEstaAtivo(rs.getBoolean("esta_ativo"));

                Localizacao localizacao = new Localizacao();
                localizacao.setId(rs.getInt("id_localizacao"));
                alerta.setLocalizacao(localizacao);

                Usuario criador = new Usuario();
                criador.setId(rs.getInt("id_criador"));
                alerta.setCriador(criador);

                return alerta;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void atualizar(Object objeto) {
        if (!(objeto instanceof Alerta)) {
            throw new IllegalArgumentException("Objeto deve ser do tipo Alerta.");
        }
        Alerta alerta = (Alerta) objeto;

        String sql = "UPDATE tb_alerta SET tipo = ?, descricao = ?, data = ?, hora = ?, esta_ativo = ?, id_localizacao = ?, id_criador = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(alerta.getTipo()));
            ps.setString(2, alerta.getDescricao());
            ps.setDate(3, new java.sql.Date(alerta.getData().getTime()));
            ps.setTime(4, alerta.getHora());
            ps.setBoolean(5, alerta.isEstaAtivo());
            ps.setInt(6, alerta.getLocalizacao().getId());
            ps.setInt(7, alerta.getCriador().getId());
            ps.setInt(8, alerta.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM tb_alerta WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        ArrayList<Object> alertas = new ArrayList<>();
        String sql = "SELECT id, tipo, descricao, data, hora, esta_ativo, id_localizacao, id_criador FROM tb_alerta";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Alerta alerta = new Alerta();
                alerta.setId(rs.getInt("id"));
                alerta.setTipo(Enum.valueOf(Alerta_Cidadao.enums.TipoAlerta.class, rs.getString("tipo")));
                alerta.setDescricao(rs.getString("descricao"));
                alerta.setData(rs.getDate("data"));
                alerta.setHora(rs.getTime("hora"));
                alerta.setEstaAtivo(rs.getBoolean("esta_ativo"));

                Localizacao localizacao = new Localizacao();
                localizacao.setId(rs.getInt("id_localizacao"));
                alerta.setLocalizacao(localizacao);

                Usuario criador = new Usuario();
                criador.setId(rs.getInt("id_criador"));
                alerta.setCriador(criador);

                alertas.add(alerta);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return alertas;
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        System.out.println("Ainda não implementado, usando Lazy Loading como padrão.");
        return listarTodosLazyLoading();
    }
}