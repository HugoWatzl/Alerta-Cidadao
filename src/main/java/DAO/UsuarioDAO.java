package DAO;

import Alerta_Cidadao.model.Usuario;
import java.sql.*;
import BD.ConnectionFactory;


public class UsuarioDAO {

    private final ConnectionFactory connectionFactory;

    public UsuarioDAO() {
        this.connectionFactory = new ConnectionFactory();
    }


    public void salvar(Usuario usuario) {
        String sql = "INSERT INTO tb_usuario (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection conn = connectionFactory.recuperaConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }


    public Usuario buscarPorId(int id) {
        String sql = "SELECT id, nome, email, senha FROM tb_usuario WHERE id = ?";
        Usuario usuario = null;
        try (Connection conn = connectionFactory.recuperaConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return usuario;
    }


    public void atualizar(Usuario usuario) {
        String sql = "UPDATE tb_usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try (Connection conn = connectionFactory.recuperaConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setInt(4, usuario.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }


    public void deletar(int id) {
        String sql = "DELETE FROM tb_usuario WHERE id = ?";
        try (Connection conn = connectionFactory.recuperaConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}