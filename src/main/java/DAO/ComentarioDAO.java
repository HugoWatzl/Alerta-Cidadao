package DAO;

import BD.ConnectionFactory;
import Alerta_Cidadao.model.Comentario;
import Alerta_Cidadao.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO implements BaseDAO {

    private Connection connection;

    public ComentarioDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void salvar(Object objeto) {
        if (!(objeto instanceof Comentario)) {
            throw new IllegalArgumentException("Objeto deve ser do tipo Comentario.");
        }
        Comentario comentario = (Comentario) objeto;

        String sql = "INSERT INTO tb_comentario (texto, data_hora, id_usuario, id_alerta) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, comentario.getTexto());
            ps.setTimestamp(2, new Timestamp(comentario.getDataHora().getTime()));
            ps.setInt(3, comentario.getUsuario().getId());
            ps.setInt(4, comentario.getIdAlerta());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    comentario.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object buscarPorId(int id) {
        String sql = "SELECT id, texto, data_hora, id_usuario, id_alerta FROM tb_comentario WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Comentario comentario = new Comentario();
                comentario.setId(rs.getInt("id"));
                comentario.setTexto(rs.getString("texto"));
                comentario.setDataHora(rs.getTimestamp("data_hora"));

                int usuarioId = rs.getInt("id_usuario");
                Usuario usuario = new Usuario();
                usuario.setId(usuarioId);
                comentario.setUsuario(usuario);

                comentario.setIdAlerta(rs.getInt("id_alerta"));
                return comentario;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void atualizar(Object objeto) {
        if (!(objeto instanceof Comentario)) {
            throw new IllegalArgumentException("Objeto deve ser do tipo Comentario.");
        }
        Comentario comentario = (Comentario) objeto;

        String sql = "UPDATE tb_comentario SET texto = ?, data_hora = ?, id_usuario = ?, id_alerta = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, comentario.getTexto());
            ps.setTimestamp(2, new Timestamp(comentario.getDataHora().getTime()));
            ps.setInt(3, comentario.getUsuario().getId());
            ps.setInt(4, comentario.getIdAlerta());
            ps.setInt(5, comentario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM tb_comentario WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Comentario> listarPorAlertaId(int alertaId) {
        String sql = "SELECT id, texto, data_hora, id_usuario, id_alerta FROM tb_comentario WHERE id_alerta = ?";
        List<Comentario> comentarios = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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
            throw new RuntimeException("Erro ao listar comentários por Alerta ID: " + e.getMessage(), e);
        }
        return comentarios;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        ArrayList<Object> comentarios = new ArrayList<>();
        String sql = "SELECT id, texto, data_hora, id_usuario, id_alerta FROM tb_comentario";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Comentario comentario = new Comentario();
                comentario.setId(rs.getInt("id"));
                comentarios.add(comentario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comentarios;
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        System.out.println("Ainda não implementado, usando Lazy Loading como padrão.");
        return listarTodosLazyLoading();
    }
}