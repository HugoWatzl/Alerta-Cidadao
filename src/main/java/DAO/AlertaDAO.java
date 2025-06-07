package DAO;


import BD.ConnectionFactory;
import Alerta_Cidadao.model.Alerta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Alerta_Cidadao.model.Localizacao;
import Alerta_Cidadao.model.Usuario;


public class AlertaDAO {

    private final ConnectionFactory connectionFactory;

    public AlertaDAO() {
        this.connectionFactory = new ConnectionFactory();
    }


    public void salvar(Alerta alerta) {
        String sql = "INSERT INTO tb_alerta (tipo, descricao, data, hora, esta_ativo, id_localizacao, id_criador) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectionFactory.recuperaConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, String.valueOf(alerta.getTipo()));
            ps.setString(2, alerta.getDescricao());
            ps.setDate(3, new java.sql.Date(alerta.getData().getTime()));
            ps.setTime(4, alerta.getHora());
            ps.setBoolean(5, alerta.isEstaAtivo());
            ps.setInt(6, alerta.getLocalizacao().getId());
            ps.setInt(7, alerta.getCriador().getId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                alerta.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar alerta: " + e.getMessage());
        }
    }


    public Alerta buscarPorId(int id) {
        String sql = "SELECT id, tipo, descricao, data, hora, esta_ativo, id_localizacao, id_criador FROM tb_alerta WHERE id = ?";
        Alerta alerta = null;
        try (Connection conn = connectionFactory.recuperaConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                alerta = new Alerta();
                alerta.setId(rs.getInt("id"));
                alerta.setTipo(Enum.valueOf(Alerta_Cidadao.enums.TipoAlerta.class, rs.getString("tipo"))); // Convertendo String de volta para Enum
                alerta.setDescricao(rs.getString("descricao"));
                alerta.setData(rs.getDate("data"));
                alerta.setHora(rs.getTime("hora"));
                alerta.setEstaAtivo(rs.getBoolean("esta_ativo"));

                // Criando instâncias básicas de Localizacao e Usuario apenas com o ID
                Localizacao localizacao = new Localizacao();
                localizacao.setId(rs.getInt("id_localizacao"));
                alerta.setLocalizacao(localizacao);

                Usuario criador = new Usuario();
                criador.setId(rs.getInt("id_criador"));
                alerta.setCriador(criador);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar alerta: " + e.getMessage());
        }
        return alerta;
    }


    public List<Alerta> listarTodos() {
        String sql = "SELECT id, tipo, descricao, data, hora, esta_ativo, id_localizacao, id_criador FROM tb_alerta";
        List<Alerta> alertas = new ArrayList<>();
        try (Connection conn = connectionFactory.recuperaConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
            System.err.println("Erro ao listar alertas: " + e.getMessage());
        }
        return alertas;
    }


    public void atualizar(Alerta alerta) {
        String sql = "UPDATE tb_alerta SET tipo = ?, descricao = ?, data = ?, hora = ?, esta_ativo = ?, id_localizacao = ?, id_criador = ? WHERE id = ?";
        try (Connection conn = connectionFactory.recuperaConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, String.valueOf(alerta.getTipo()));
            ps.setString(2, alerta.getDescricao());
            ps.setDate(3, new java.sql.Date(alerta.getData().getTime()));
            ps.setTime(4, alerta.getHora());
            ps.setBoolean(5, alerta.isEstaAtivo());
            ps.setInt(6, alerta.getLocalizacao().getId()); // Correção aqui
            ps.setInt(7, alerta.getCriador().getId());     // Correção aqui
            ps.setInt(8, alerta.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar alerta: " + e.getMessage());
        }
    }


    public void deletar(int id) {
        String sql = "DELETE FROM tb_alerta WHERE id = ?";
        try (Connection conn = connectionFactory.recuperaConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar alerta: " + e.getMessage());
        }
    }
}