package DAO;

import BD.ConnectionFactory;
import Alerta_Cidadao.model.Comentario;
import Alerta_Cidadao.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {

    private final ConnectionFactory connectionFactory;

    public ComentarioDAO() {
        this.connectionFactory = new ConnectionFactory();
    }


    public void salvar(Comentario comentario) {
        String sql = "INSERT INTO tb_comentario (texto, data_hora, id_usuario, id_alerta) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectionFactory.recuperaConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, comentario.getTexto());
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis())); // Usando a data e hora atuais
            ps.setInt(3, comentario.getUsuario().getId());
            ps.setInt(4, comentario.getIdAlerta()); // Correção aqui
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                comentario.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar comentário: " + e.getMessage());
        }
    }


    public Comentario buscarPorId(int id) {
        String sql = "SELECT id, texto, data_hora, id_usuario, id_alerta FROM tb_comentario WHERE id = ?";
        Comentario comentario = null;
        try (Connection conn = connectionFactory.recuperaConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                comentario = new Comentario();
                comentario.setId(rs.getInt("id"));
                comentario.setTexto(rs.getString("texto"));
                comentario.setDataHora(rs.getTimestamp("data_hora"));
                // Para buscar o objeto Usuario completo, você precisaria de outra consulta
                int usuarioId = rs.getInt("id_usuario");
                Usuario usuario = new Usuario(); // Você pode instanciar um Usuario com o ID
                usuario.setId(usuarioId);
                comentario.setUsuario(usuario);
                comentario.setIdAlerta(rs.getInt("id_alerta"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar comentário: " + e.getMessage());
        }
        return comentario;
    }


    public List<Comentario> listarPorAlertaId(int alertaId) {
        String sql = "SELECT id, texto, data_hora, id_usuario, id_alerta FROM tb_comentario WHERE id_alerta = ?";
        List<Comentario> comentarios = new ArrayList<>();
        try (Connection conn = connectionFactory.recuperaConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, alertaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Comentario comentario = new Comentario();
                comentario.setId(rs.getInt("id"));
                comentario.setTexto(rs.getString("texto"));
                comentario.setDataHora(rs.getTimestamp("data_hora"));
                int usuarioId = rs.getInt("id_usuario");
                Usuario usuario = new Usuario();
                usuario.setId(usuarioId);
                comentario.setUsuario(usuario);
                comentario.setIdAlerta(rs.getInt("id_alerta"));
                comentarios.add(comentario);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar comentários por Alerta ID: " + e.getMessage());
        }
        return comentarios;
    }


    public void atualizar(Comentario comentario) {
        String sql = "UPDATE tb_comentario SET texto = ?, data_hora = ?, id_usuario = ?, id_alerta = ? WHERE id = ?";
        try (Connection conn = connectionFactory.recuperaConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, comentario.getTexto());
            ps.setTimestamp(2, (Timestamp) comentario.getDataHora());
            ps.setInt(3, comentario.getUsuario().getId());
            ps.setInt(4, comentario.getIdAlerta()); // Correção aqui
            ps.setInt(5, comentario.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar comentário: " + e.getMessage());
        }
    }


    public void deletar(int id) {
        String sql = "DELETE FROM tb_comentario WHERE id = ?";
        try (Connection conn = connectionFactory.recuperaConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar comentário: " + e.getMessage());
        }
    }
}